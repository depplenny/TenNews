package com.depplenny.tennews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{
    // Store instance variables
    List<News> mNewsList;
    NewsAdapter mNewsAdapter;
    RecyclerView mNewsListRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Bundle mBundle;
    ProgressBar mProgressBar;
    public String mQueryString;


    // newInstance constructor for creating fragment with arguments
    public static MyFragment newInstance(String queryString) {
        MyFragment myFragment = new MyFragment();

        Bundle args = new Bundle();
        args.putString("queryString", queryString);
        myFragment.setArguments(args);

        return myFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueryString = getArguments().getString("queryString");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        mProgressBar = view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        // Lookup the recyclerview in fragment_my.xml
        mNewsListRecyclerView = view.findViewById(R.id.newsListRecyclerView);

        Log.d("xz", "onCreateView()"+mQueryString);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", mQueryString);
        LoaderManager.getInstance(this).initLoader(0, queryBundle, this);

    }



    //Here we will initiate AsyncTaskLoader and handle task in background
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        Log.d("xz", "onCreateLoader()"+mQueryString);

        return new NetworkAsyncTaskLoader(getActivity(), queryString);
    }
    //After getting result we will update our UI here?
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        mProgressBar.setVisibility(View.GONE);

        mNewsList = newsList;
        // Create adapter passing in the sample data
        mNewsAdapter = new NewsAdapter(mNewsList);
        // Attach the adapter to the recyclerview to populate items
        mNewsListRecyclerView.setAdapter(mNewsAdapter);
        // Set layout manager to position the items
        mLayoutManager = new LinearLayoutManager(getActivity());
        mNewsListRecyclerView.setLayoutManager(mLayoutManager);

        if (mBundle != null) {
            mLayoutManager.onRestoreInstanceState(mBundle.getParcelable("rvState"));
        }


        Log.d("xz", "onLoadFinished()"+mQueryString);
    }
    //Leave it for now as it is
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
    }

    @Override
    public void onPause() {
        super.onPause();

        Parcelable parcelable = mLayoutManager.onSaveInstanceState();
        mBundle = new Bundle();
        mBundle.putParcelable("rvState", parcelable);

        Log.d("xz", "onPause()"+mQueryString);
    }
}