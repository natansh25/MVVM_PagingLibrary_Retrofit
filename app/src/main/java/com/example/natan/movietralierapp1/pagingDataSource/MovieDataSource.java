package com.example.natan.movietralierapp1.pagingDataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.natan.movietralierapp1.Network.NetworkState;
import com.example.natan.movietralierapp1.model.Example;
import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Result> {

    private static final String TAG = MovieDataSource.class.getSimpleName();


    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private Executor retryExecutor;
    ApiInterface mApiInterface;

    public MovieDataSource(Executor retryExecutor) {
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
        this.retryExecutor = retryExecutor;
        mApiInterface = ApiClient.getClient();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Result> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        mApiInterface.fetchMoviesPaging("popular", ApiClient.api_key, 1, params.requestedLoadSize)
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful()) {
                            String uri = call.request().url().toString();
                            Log.d("urlxxxInitial", uri);
                            callback.onResult(response.body().getResults(), null, 2l);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);
                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }


                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Log.d("urlxxxInitial", "faliure");

                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

                    }
                });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Result> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);


        mApiInterface.fetchMoviesPaging(ApiClient.api_key, "popular", params.key, params.requestedLoadSize)
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful()) {
                            long nextKey = (params.key == response.body().getTotalResults()) ? null : params.key + 1;
                            String uri = call.request().url().toString();
                            Log.d("urlxxxAfter", uri);
                            callback.onResult(response.body().getResults(), nextKey);
                            networkState.postValue(NetworkState.LOADED);

                        } else
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });


    }
}
