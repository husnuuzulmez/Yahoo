package com.example.uzulmez.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Uzulmez on 2/8/2018.
 */
public class Adaptor extends RecyclerView.Adapter<Adaptor.CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    public RecyclerView Rview;
    public Feeds feeds;
    private FeedItem SelectedFeed ;



    public Adaptor(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.feeds = new Feeds();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        FeedItem feedItem = feedItemList.get(i);

        //Render image using Picasso library
        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.imageView);
        }

        customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));

        if (i == feedItemList.size() - 10) {
            DownloadTask2 myTask= new DownloadTask2(mContext,Rview, feeds,this);
            MainActivity.page++;

            myTask.execute(MainActivity.page);

        }

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectedFeed = feedItemList.get(getAdapterPosition());
                    Bundle extra = new Bundle();
                    extra.putSerializable("feeditem", SelectedFeed);



                    Intent i = new Intent(mContext, Photo.class);
                    i.putExtra("extra", extra);
                    mContext.startActivity(i);
                }
            });


        }
    }


  /*  public void setOnItemClickListener(OnRecyclerViewItemClickListener<ViewModel> listener) {
        this.itemClickListener = listener;
    }
*/


}