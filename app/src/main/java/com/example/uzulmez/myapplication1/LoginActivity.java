package com.example.uzulmez.myapplication1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Uzulmez on 2/14/2018.
 */

public class LoginActivity  extends AppCompatActivity implements GestureDetector.OnGestureListener {

   private ImageButton imgbtn;
    GestureDetector gestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        gestureDetector = new GestureDetector(LoginActivity.this,LoginActivity.this);

        final ImageView imageView  = (ImageView) findViewById(R.id.imageView3);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        if(motionEvent1.getX() - motionEvent.getX() > 10) {
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            startActivity(i);

            return true;
        }
        return true;
    }


}
