package com.example.natan.movietralierapp1.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.natan.movietralierapp1.database.RemoteNetworkCall;
import com.example.natan.movietralierapp1.model.Result;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    List<Result> mResults;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mResults = RemoteNetworkCall.fetchData("popular");

    }

    public List<Result> getAllMovies() {
        return mResults;
    }


}
