package com.example.myapplication.animation.onboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.os.Handler;



@SuppressLint("ViewConstructor")
public class SocialMediaAnimation extends androidx.appcompat.widget.AppCompatImageButton {

    /*
    this class is responsible for providing the animation for the social media Fragment
     */

    /*
    Constants
     */
    private final Handler myHandler = new Handler();
    /*
    These Constants will be set with Methods and will give
    the possibility to know the length of the frame
     */
    private int minX =0;
    private int maxX =0;
    private int minY=0;
    private int maxY=0;
    // Speed how fast the Buttons will be moved
    private int xSpeed;
    private int ySpeed;
    // Refresh Rate
    private final long myDelay =70;

    /*
    Constructor
     */
    public SocialMediaAnimation(Context context) {
        super(context);
    }

    public SocialMediaAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Animation if the animation was set
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // if x in field
            if(getX()>=minX && getX()<=maxX){
                //if y not in field
                if (!(getY() >= minY) || !(getY() <= maxY)) {
                    // turn y duration
                    ySpeed =  (ySpeed * (-1));
                }
            }
            else{
                // turn x duration
                xSpeed=(xSpeed*(-1));
            }
            // add speed to coordinates
            setX(getX()+xSpeed);
            setY(getY()+ySpeed);
            myHandler.postDelayed(runnable,myDelay);
        }
    };

    /*
    Set X Length
     */
    public void setX(int minX,int maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    /*
    Set Y Length
     */
    public void setY(int minY,int maxY) {
        this.minY = minY;
        this.maxY = maxY;
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
        this.ySpeed = ySpeed;
    }

    /*
    start Animation
     */
    public void animateBubbles(){
        myHandler.removeCallbacks(runnable);
        myHandler.postDelayed(runnable, myDelay);
    }

    /*
    Stop Animation
     */
    public void stopAnimation(){
        myHandler.removeCallbacks(runnable);
    }
}
