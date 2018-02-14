package com.example.uzulmez.myapplication1;

import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

/**
 * Created by Uzulmez on 2/12/2018.
 */

public class MyViewModel extends ViewModel {
    public  Feeds feeds;

    public MyViewModel() {
        feeds = new Feeds();
    }

}
