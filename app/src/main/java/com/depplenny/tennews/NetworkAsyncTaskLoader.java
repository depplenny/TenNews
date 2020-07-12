package com.depplenny.tennews;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

public class NetworkAsyncTaskLoader extends AsyncTaskLoader<List<News>> {

    String mQueryString;

    public NetworkAsyncTaskLoader(Context context, String queryString) {
        super(context);
        mQueryString = queryString;

    }

    //Think of this as AsyncTask doInBackground() method, here you will actually initiate Network call, or any work that need to be done on background
    @Override
    public List<News> loadInBackground() {
        List<News> newsList;

        newsList = NetworkUtils.getNewsList(mQueryString);

        return newsList;
    }

    @Override
    protected void onStartLoading() {
        //Think of this as AsyncTask onPreExecute() method,you can start your progress bar, and at the end call forceLoad();
        forceLoad();
    }


}
