package com.example.uzulmez.myapplication1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uzulmez on 2/12/2018.
 */

class Feeds {
    private List<FeedItem> feedsList;

    public Feeds (){
        feedsList = new ArrayList<>();
    }

    public void Setfeed (List<FeedItem> Fl){
        this.feedsList = Fl;
    }

    public List<FeedItem>  Addfeed (List<FeedItem> AddFl){
        for (int i=0; i<AddFl.size(); i++){
            feedsList.add(AddFl.get(i));
        }
        return feedsList;
    }

    public List<FeedItem> getfeeds() {
        return this.feedsList;
    }
}
