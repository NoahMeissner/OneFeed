package com.example.myapplication.animation.addSource;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Objects;

/*
 Detects swipes across a view.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    /*
    Variables
     */
    private  GestureDetector gestureDetector;
    private final View view;
    private final Gesture gesture;


    /*
    Constructor
     */
    public OnSwipeTouchListener(View view, Gesture gesture){
        this.view = view;
        this.gesture = gesture;
    }

    /*
    This Method sets an Gesture Listener
     */
    public void setGestureListener(){
        int threshold = 100;
        int velocity_threshold = 100;
        GestureDetector.SimpleOnGestureListener listener =
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1,
                                           MotionEvent e2,
                                           float velocityX,
                                           float velocityY) {

                        float xDiff = e2.getX() - e1.getX();
                        /*
                       checks Conditions to know which Gesture was pressed
                         */
                        try {
                                if(xDiff != 0) {
                                    if (Math.abs((xDiff)) > threshold
                                            && Math.abs(velocityX) > velocity_threshold) {

                                        if (xDiff > 0) {
                                            Log.d("Swiped", "Right");
                                            gesture.gestureHasDetected(Swipe.Right);
                                        } else {
                                            Log.d("Swiped", "Left");
                                            gesture.gestureHasDetected(Swipe.Left);
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return  gestureDetector.onTouchEvent(motionEvent);
    }

    public interface Gesture{
        void gestureHasDetected(Swipe swipe);
    }
}