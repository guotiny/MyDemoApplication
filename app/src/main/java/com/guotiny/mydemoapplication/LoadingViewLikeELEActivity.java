package com.guotiny.mydemoapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guotiny.mydemoapplication.loadingview.BounceLoadingView;

public class LoadingViewLikeELEActivity extends AppCompatActivity {


    private BounceLoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view_like_ele);
        //
        loadingView = (BounceLoadingView) findViewById(R.id.loadingView);

        loadingView.addBitmap(R.drawable.v4);
        loadingView.addBitmap(R.drawable.v5);
        loadingView.addBitmap(R.drawable.v6);
        loadingView.addBitmap(R.drawable.v7);
        loadingView.addBitmap(R.drawable.v8);
        loadingView.addBitmap(R.drawable.v9);
        loadingView.setShadowColor(Color.LTGRAY);
        loadingView.setDuration(700);
        loadingView.start();
    }


}
