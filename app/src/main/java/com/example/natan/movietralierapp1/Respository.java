package com.example.natan.movietralierapp1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.natan.movietralierapp1.database.RemoteNetworkCall;
import com.example.natan.movietralierapp1.model.Result;

import java.util.List;

public class Respository {

    private LiveData<List<Result>> mData;


    public Respository(Application application) {


        RemoteNetworkCall.fetchData("popular");


    }

    public LiveData<List<Result>> mLiveData() {
        mData = RemoteNetworkCall.getIntData();
        return mData;
    }


    public void getTopRated() {
        RemoteNetworkCall.fetchData("top_rated");
    }

    public void getPopular() {
        RemoteNetworkCall.fetchData("popular");
    }


}
