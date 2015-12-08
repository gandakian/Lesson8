package com.almightyamir.tomandjerry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Bitmap splash;
    Canvas canvas;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
        mainLayout.setBackgroundResource(R.drawable.tom_and_jerry_splash);

        /*splash = BitmapFactory.decodeResource(getResources(), R.drawable.tom_and_jerry_splash);
        splash = Bitmap.createScaledBitmap(splash, getWidth(), getHeight(), false);
        canvas.drawBitmap(splash, 0, 0, null);*/

        TextView heading = new TextView(this);
        heading.setGravity(Gravity.CENTER);
        heading.setText("TOM AND JERRY the game");
        heading.setTextSize(20);
        mainLayout.addView(heading);

        TextView description = new TextView(this);
        description.setGravity(Gravity.CENTER);
        description.setText("Release Tom to Catch the notorious moving Jerry and earn scores.");
        description.setTextSize(20);
        mainLayout.addView(description);


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


        setContentView(mainLayout);

    }

}
