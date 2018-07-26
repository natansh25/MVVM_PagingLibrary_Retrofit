package com.example.natan.movietralierapp1.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.natan.movietralierapp1.database.RemoteNetworkCall;
import com.example.natan.movietralierapp1.model.Example;
import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Result>> mData;


    public MainViewModel(@NonNull Application application) {
        super(application);


        mData = RemoteNetworkCall.getIntData();
        //mResults = RemoteNetworkCall.fetchData("popular");
        /*mResults = RemoteNetworkCall.fetchData("popular");*/

    }


}
