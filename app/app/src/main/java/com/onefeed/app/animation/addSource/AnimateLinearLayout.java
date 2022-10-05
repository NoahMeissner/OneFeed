package com.onefeed.app.animation.addSource;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class AnimateLinearLayout extends FrameLayout {

    /*
    This class is responsible for the delete animation of the source items.
     */

    // Constants
    private int rotation = 2;
    private final long MY_DELAY =80;
    private final Handler myHandler = new Handler();



    /*
    runnable to outsource the calculation of the animation into a new thread
     */
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /*
            The item is always rotated and then the rotation variable
             is multiplied by -1 to reverse the animation.
             */
            setRotation(rotation);
            myHandler.postDelayed(runnable, MY_DELAY);
            rotation=-rotation;
        }
    };

    /*
    this method is responsible for starting the animation
     */
    public void animateItems(){
        myHandler.removeCallbacks(runnable);
        myHandler.postDelayed(runnable, MY_DELAY);
    }

    public void stopAnimation(){
        int startPoint = 0;
        setRotation(startPoint);
        myHandler.removeCallbacksAndMessages(null);
    }

    public AnimateLinearLayout(Context context) {
        super(context);
    }

    public AnimateLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
