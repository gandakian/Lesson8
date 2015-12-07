package com.almightyamir.tomandjerry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Almighty Amir on 03-Dec-15.
 */

public class GameView extends SurfaceView {

    private SurfaceHolder holder;
    private Bitmap jerry, background, tom;
    private GameThread gthread = null;

    // horizontal position (graphic is 205 pixels wide thus initialize right edge of graphic fall to left screen edge)
    private float jerryX = -205.0f;
    private float jerryY = 80.0f; // vertical position

    private float tomX = -50.0f;
    private float tomY = -101.0f;
    private boolean tomActive = false;

    private int score = 0;
    private int missedScore = 0;
    private Paint scorePaint, missedScorePaint;

    public GameView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback( new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                jerry = BitmapFactory.decodeResource(getResources(), R.drawable.jerry_mouse);
                tom = BitmapFactory.decodeResource(getResources(), R.drawable.tom_cat);

                background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
                background = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), false);

                scorePaint = new Paint();
                scorePaint.setColor(Color.BLACK);
                scorePaint.setTextSize(50.0f);

                missedScorePaint = new Paint();
                missedScorePaint.setColor(Color.BLACK);
                missedScorePaint.setTextSize(50.0f);

                makeThread();

                gthread.setRunning(true);
                gthread.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        } );
    }

    public void makeThread()
    {
        gthread = new GameThread(this);

    }

    public void killThread()
    {
        boolean retry = true;
        gthread.setRunning(false);
        while(retry)
        {
            try
            {
                gthread.join();
                retry = false;
            } catch (InterruptedException e)
            {
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);

        //Log.e("TomY 0 ", "" + tomY);

        if(tomActive)
        {
            Log.e("Release Tom","Hello "+tomActive);

            tomY = tomY - 5; // tom travels up the screen 5 pixels per redraw
            Log.e("TomY 1 ", ""+tomY);
            if ( tomY < 300 ) // if the tom goes beyond the bottom of the jerry graphic by 25 pixels
            {
                Log.e("TomY ", ""+tomY);
                tomX = -50.0f; // park tom
                tomY = -101.0f; // and
                tomActive = false; // Turn off tom drawing
            }
            else // otherwise draw the tom in its new position
            {
                canvas.drawBitmap(tom, tomX, tomY, null);
            }
        }

        jerryX = jerryX + 3.0f;
        if(jerryX > getWidth()) jerryX = -205.0f;

        canvas.drawBitmap(jerry, jerryX, jerryY, null);

        if ( tomX >= jerryX && tomX <= jerryX + jerry.getWidth()
                && tomY <= jerryY + jerry.getHeight() && tomY >= jerryY + jerry.getHeight() - 25.0f )
        {
            score++;
            tomActive = false;
            tomX = -50.0f;
            tomY = -101.0f;
        }

        /*else
        {
            missedScore++;
            tomActive = false;
            tomX = -50.0f;
            tomY = -101.0f;
        }*/

        //canvas.drawText("Missed: " + String.valueOf(missedScore), 320.0f, 50.0f, scorePaint);

        //canvas.drawColor(Color.WHITE);
        //canvas.drawBitmap(jerry, 50, 25, null);

    }

    public void releaseTom(){

        Log.e("Release Tom", "Started");

        tomActive = true;
        tomX = getWidth() / 2.0f - tom.getWidth() / 2;
        tomY = getHeight() - tom.getHeight() - 25;
        Log.e("Release Tom", "Ended "+tomActive);

        Log.e("Tom Height", ""+tom.getHeight());

    }

    public void onDestroy()
    {
        jerry.recycle();
        jerry = null;
        System.gc();
    }



}
