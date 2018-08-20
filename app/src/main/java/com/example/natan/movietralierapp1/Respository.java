package com.example.natan.movietralierapp1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.natan.movietralierapp1.Network.NetworkState;
import com.example.natan.movietralierapp1.database.RemoteNetworkCall;
import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.pagingDataSource.MovieDataFactory;
import com.example.natan.movietralierapp1.pagingDataSource.MovieDataSource;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Respository {

    public LiveData<PagedList<Result>> userList;
    public LiveData<NetworkState> networkState;
    Executor executor;
    LiveData<MovieDataSource> tDataSource;


    public Respository(Application application) {


        executor = Executors.newFixedThreadPool(5);
        MovieDataFactory movieDataFactory = new MovieDataFactory(executor);
        tDataSource = movieDataFactory.getMutableLiveData();

        networkState = Transformations.switchMap(movieDataFactory.getMutableLiveData(), dataSource -> {
            return dataSource.getNetworkState();
        });

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        userList = (new LivePagedListBuilder(movieDataFactory, pagedListConfig))
                .build();

        Log.d("pageData", String.valueOf(userList));


    }


    public LiveData<PagedList<Result>> getData() {
        return userList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

   /* private LiveData<List<Result>> mData;



    public LiveData<List<Result>> mLiveData() {
        mData = RemoteNetworkCall.getIntData();
        return mData;
    }


    public void getTopRated() {
        RemoteNetworkCall.fetchData("top_rated");
    }

    public void getPopular() {
        RemoteNetworkCall.fetchData("popular");
    }*/


}
