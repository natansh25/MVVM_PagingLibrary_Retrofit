package com.example.natan.movietralierapp1.pagingDataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;

import java.util.concurrent.Executor;

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
       mApiInterface=ApiClient.getClient();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Result> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Result> callback) {

    }
}
