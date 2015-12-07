package com.almightyamir.tomandjerry;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements View.OnClickListener{

    private GameView gv;
    private Button tomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        gv = new GameView(this);

        tomButton = new Button(this);
        tomButton.setWidth(250);
        tomButton.setHeight(70);
        tomButton.setBackgroundColor(Color.LTGRAY);
        tomButton.setTextColor(Color.RED);
        tomButton.setTextSize(20);
        tomButton.setText("Release Tom");
        tomButton.setOnClickListener(this);
        tomButton.setGravity(Gravity.CENTER);

        FrameLayout gameLayout = new FrameLayout(this);

        LinearLayout buttonLayout = new LinearLayout (this);
        buttonLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        buttonLayout.addView(tomButton);
        gameLayout.addView(gv);
        gameLayout.addView(buttonLayout);

        setContentView(gameLayout);

    }

    @Override
    protected void onPause() {

        super.onPause();
        gv.killThread(); //Notice this reaches into the GameView object and runs the killThread mehtod.
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        gv.onDestroy();
    }

    @Override
    public void onClick(View v) {

        Log.e("Button","Clicked");
        gv.releaseTom();
    }
}
