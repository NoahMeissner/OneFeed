package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Detects left and right swipes across a view.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    // Initialise Variable
    GestureDetector gestureDetector;
    TextView textView;

    // Constructor
    public OnSwipeTouchListener(View view, TextView textView){
        // Initialize threshold value
        this.textView = textView;
        int threshold = 100;
        int velocity_threshold = 100;

        // Initial simple gesture listener
        GestureDetector.SimpleOnGestureListener listener =
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        float xDiff = e2.getX() - e1.getX();
                        float yDiff = e2.getY() - e1.getY();
                        try{
                            if(Math.abs(xDiff) > Math.abs(yDiff)){
                                if(Math.abs((xDiff))>threshold && Math.abs(velocityX)>velocity_threshold){
                                    if (xDiff > 0) {
                                        textView.setText("Right");
                                        Log.d("Swiped","Right") ;
                                    }
                                    else{
                                        textView.setText("Left");
                                        Log.d("Swiped","Left");
                                    }
                                    return true;
                                }
                            }else{
                                if(Math.abs(yDiff)>threshold && Math.abs(velocityY)>velocity_threshold){
                                    if(yDiff >0){
                                        textView.setText("Down");
                                        Log.d("swiped","down");
                                    }
                                    else{
                                        textView.setText("UP");
                                        Log.d("swiped","up");
                                    }
                                    return true;
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        return false;
                    }
                };
        gestureDetector = new GestureDetector(listener);
        view.setOnTouchListener(this);

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return  gestureDetector.onTouchEvent(motionEvent);
    }
}