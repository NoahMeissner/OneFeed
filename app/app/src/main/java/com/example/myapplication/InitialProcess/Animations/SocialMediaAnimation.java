package com.example.myapplication.InitialProcess.Animations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.os.Handler;



@SuppressLint("ViewConstructor")
public class SocialMediaAnimation extends androidx.appcompat.widget.AppCompatImageButton {

    /*
    this class is responsible for providing the animation for the social media Fragment
     */

    private final Handler myHandler = new Handler();
    private int minX =0;
    private int maxX =0;
    private int minY=0;
    private int maxY=0;
    private int xSpeed;
    private int ySpeed;
    private final long myDelay =70;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(getX()>=minX && getX()<=maxX){
                if (!(getY() >= minY) || !(getY() <= maxY)) {
                    ySpeed =  (ySpeed * (-1));
                }
            }
            else{
                xSpeed=(xSpeed*(-1));
            }
            setX(getX()+xSpeed);
            setY(getY()+ySpeed);
            myHandler.postDelayed(runnable,myDelay);
        }
    };

    public void setX(int minX,int maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }


    public void setY(int minY,int maxY) {
        this.minY = minY;
        this.maxY = maxY;
    }


    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void animateBubbles(){
        myHandler.removeCallbacks(runnable);
        myHandler.postDelayed(runnable, myDelay);
    }

    public void stopAnimation(){
        myHandler.removeCallbacks(runnable);
    }

    public SocialMediaAnimation(Context context) {
        super(context);
    }

    public SocialMediaAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
