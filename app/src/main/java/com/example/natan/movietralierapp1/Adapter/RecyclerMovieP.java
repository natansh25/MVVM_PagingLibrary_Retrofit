package com.example.natan.movietralierapp1.Adapter;

import android.arch.paging.PagedListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.natan.movietralierapp1.MainActivity;
import com.example.natan.movietralierapp1.Network.NetworkState;
import com.example.natan.movietralierapp1.R;
import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.picasso.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerMovieP extends PagedListAdapter<Result, RecyclerMovieP.MyViewHolder> {


    private List<Result> mMovieList;
    private NetworkState networkState;
    //Implementing on click listner
     private ListItemClickListener mOnClickListener;

    //Interface

    public interface ListItemClickListener {
        void onListItemClick(Result movie);
    }


    public RecyclerMovieP( ListItemClickListener listener) {
        super(Result.DIFF_CALLBACK);


        this.mOnClickListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);

        return new MyViewHolder(itemView);*/
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == R.layout.custom_list) {
            view = layoutInflater.inflate(R.layout.custom_list, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == R.layout.network_state_item) {
         /*   view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view, itemClickListener);*/
            return null;
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

     /*   Result movie = mMovieList.get(position);

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).transform(new RoundedTransformation(14, 0)).into(holder.img_movie);
        // holder.bind(mMovieList.get(position), mOnClickListener);
        //ViewCompat.setTransitionName(holder.img_movie, movie.getTitle());*/
        switch (getItemViewType(position)) {

            case R.layout.custom_list:
                Result movie = mMovieList.get(position);
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).transform(new RoundedTransformation(14, 0)).into(holder.img_movie);

                //((MyViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                /*((NetworkStateItemViewHolder) holder).bindView(networkState);*/
                break;
        }


    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }


    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.custom_list;
        }
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageView)
        ImageView img_movie;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            Result result = mMovieList.get(adapterPosition);
            mOnClickListener.onListItemClick(result);


        }
    }



    static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progressBar;
        private final TextView errorMsg;
        private Button button;


        public NetworkStateItemViewHolder(View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            errorMsg = itemView.findViewById(R.id.error_msg);
            button = itemView.findViewById(R.id.retry_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*listItemClickListener.onRetryClick(view, getAdapterPosition());*/
                }
            });
        }


        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.getMsg());
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
