package com.onefeed.app.animation.addSource;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeListener implements OnTouchListener {

    // Constant
    private final GestureDetector gestureDetector;
    private final SwipeListener listener;

    /*
    Constructor
     */
    public OnSwipeListener(Context ctx, SwipeListener listener){
        this.listener = listener;
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        /*
        Constants
         */
        private static final int THRESHOLD = 100;
        private static final int VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /*
        in this Method the gestures will be calculate
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean outCome = false;
            try {
                /*
                These will calculate the difference between the first and the second contact with
                the Display
                 */
                float xDiff = e2.getX() - e1.getX();
                float yDiff = e2.getY() - e1.getY();
                if (Math.abs(xDiff) > Math.abs(yDiff)) {
                    if (Math.abs(yDiff) > THRESHOLD
                            && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
                        /*
                        Swipes Bottom or to the Top will not be used but are important to prevent
                        errors
                         */
                        if (yDiff > 0) {
                            Log.d("Swipe","Bottom");
                        } else {
                            Log.d("Swipe","Top");
                        }
                        outCome = true;
                    }
                    else if (Math.abs(xDiff) > THRESHOLD
                            && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                        
                        if (xDiff > 0) {
                            listener.gesturesIsDetected(Swipe.RIGHT);
                        } else {
                            listener.gesturesIsDetected(Swipe.LEFT);
                        }
                        outCome = true;
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return outCome;
        }
    }
    public interface SwipeListener {
        void gesturesIsDetected(Swipe swipe);
    }
}