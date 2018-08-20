package com.example.natan.movietralierapp1.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.natan.movietralierapp1.Network.NetworkState;
import com.example.natan.movietralierapp1.Respository;
import com.example.natan.movietralierapp1.database.RemoteNetworkCall;
import com.example.natan.movietralierapp1.model.Example;
import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.pagingDataSource.MovieDataFactory;
import com.example.natan.movietralierapp1.pagingDataSource.MovieDataSource;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private LiveData<List<Result>> mData;
    private Respository mRespository;


    public LiveData<PagedList<Result>> userList;
    public LiveData<NetworkState> networkState;
    Executor executor;
    LiveData<MovieDataSource> tDataSource;



    public MainViewModel() {

        executor = Executors.newFixedThreadPool(5);
        MovieDataFactory movieDataFactory=new MovieDataFactory(executor);
        tDataSource=movieDataFactory.getMutableLiveData();

        networkState = Transformations.switchMap(movieDataFactory.getMutableLiveData(), dataSource -> {
            return dataSource.getNetworkState();
        });

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        userList = (new LivePagedListBuilder(movieDataFactory, pagedListConfig))
                .build();



        //mRespository = new Respository(application);

    }

  /*  public LiveData<List<Result>> mLiveData() {
        mData = mRespository.mLiveData();
        return mData;
    }


    public void getTopRated() {
        mRespository.getTopRated();
    }

    public void getPopular() {
        mRespository.getPopular();
    }*/

}
