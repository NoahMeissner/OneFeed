package com.example.myapplication.animation.addSource;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Detects swipes across a view.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    /*
    Variables
     */
    private  GestureDetector gestureDetector;


    /*
    Constructor
     */
    public OnSwipeTouchListener(View view, Gesture gesture){
        setGestureListener(view,gesture);
    }

    /*
    This Method sets an Gesture Listener
     */
    private void setGestureListener(View view, Gesture gesture){
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
                        float yDiff = e2.getY() - e1.getY();
                        /*
                       checks Conditions to know which Gesture was pressed
                         */
                        try{
                            if(Math.abs(xDiff) > Math.abs(yDiff)){
                                if(Math.abs((xDiff))>threshold
                                        && Math.abs(velocityX)>velocity_threshold){

                                    if (xDiff > 0) {
                                        Log.d("Swiped","Right") ;
                                        gesture.gestureHasDetected(Swipe.Right);
                                    }
                                    else{
                                        Log.d("Swiped","Left");
                                        gesture.gestureHasDetected(Swipe.Left);
                                    }
                                    return true;
                                }
                            }else{
                                if(Math.abs(yDiff)>threshold
                                        && Math.abs(velocityY)>velocity_threshold){

                                    if(yDiff >0){
                                        Log.d("swiped","down");
                                        gesture.gestureHasDetected(Swipe.Down);
                                    }
                                    else{
                                        Log.d("swiped","up");
                                        gesture.gestureHasDetected(Swipe.Up);
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