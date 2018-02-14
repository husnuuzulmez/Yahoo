package com.example.uzulmez.myapplication1;


import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private MyViewModel viewModel;
    private RecyclerView mRecyclerView;
    public   Feeds feeds;
    public  Adaptor adapter;
    public static int page=1;
    DownloadTask2 myTask;
    public static SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        feeds =viewModel.feeds;
        adapter =new Adaptor(this, feeds.getfeeds());
        adapter.Rview = mRecyclerView;
        mRecyclerView.setAdapter(adapter);
        myTask = new DownloadTask2(this ,mRecyclerView , feeds,adapter );
        myTask.execute(page);

 /*       adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<viewModel>() {
            @Override
            public void onItemClick(View view, ViewModel viewModel) {
                adapter.remove(viewModel);
            }
        });
*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                page=1;
                myTask = new DownloadTask2(getApplication().getBaseContext() ,mRecyclerView , feeds,adapter );
                myTask.execute(page);

            }
        });



    }


}