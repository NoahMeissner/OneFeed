package com.example.myapplication.animation.onboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class InterestsAnimation extends Button {

    /*
        this class is responsible for providing the animation for the InterestsFragment
     */


    /*
    Constants
     */
    private final Handler myHandlerClick = new Handler();
    // RefreshRate
    private long myDelay = 10;
    /*
    These Constants will be set with Methods and will give
    the possibility to know the length of the frame
     */
    private int minX =0;
    private int maxX =0;
    private int minY=0;
    private int maxY=0;
    // Speed how fast the Buttons will be moved
    private int xSpeed=0;
    private int ySpeed=0;
    /*
    constant which is important to compensate for the difference
     in length between activity and fragment
     */
    private final int spacing = 330;

    /*
    Constructor
     */
    public InterestsAnimation(Context context) {
        super(context);
    }

    /*
    Constructor
     */
    public InterestsAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Animation if the animation was set
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
                // if x in field
                if (getX() >= minX && getX() <= maxX) {
                    //if y not in the field
                    if (!(getY() >= minY) || !(getY() <= maxY)) {
                        // turn y duration
                        ySpeed = (ySpeed * (-1));
                    }
                } else {
                    // turn x duration
                    xSpeed = (xSpeed * (-1));
                }
                // add speed to coordinates
                setX(getX() + xSpeed);
                setY(getY() + ySpeed);
                myHandlerClick.postDelayed(runnable, myDelay);
                // add 1 to delay to slow down the animation
                myDelay+=1;
        }
    };

    /*
    Start Animation
     */
    public void animateInterestsClick(){
        myHandlerClick.removeCallbacks(runnable);
        myHandlerClick.postDelayed(runnable, myDelay);
    }

    /*
    Set x Length
     */
    public void setXField(int minX, int maxX) {
        this.minX = minX;
        this.maxX = maxX-spacing;
    }

    /*
    Set Y Length
     */
    public void setYField(int minY, int maxY) {
        this.minY = minY+spacing;
        this.maxY = maxY-2*spacing;
    }

    /*
    Set Delay
     */
    public void setMyDelay(long delay){
        this.myDelay = delay;
    }

    /*
    Stop Animation
     */
    public void stopAnimation(){
        myHandlerClick.removeCallbacksAndMessages(null);
    }

    /*
    Set X Speed
     */
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    /*
    Set Y Speed
     */
    public void setYSpeed(int ySpeed) {
        this.ySpeed =  ySpeed;
    }
}
