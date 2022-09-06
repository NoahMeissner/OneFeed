package com.example.myapplication.InitialProcess.Animations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.os.Handler;


@SuppressLint("ViewConstructor")
public class SocialMediaAnimation extends androidx.appcompat.widget.AppCompatImageButton {

    /*
    this class is responsible for providing the animation for the social media Fragement
     */

    private final Handler myhandler  = new Handler();
    private int minX =0;
    private int maxX =0;
    private int minY=0;
    private int maxY=0;
    private int xSpeed;
    private int ySpeed;
    private long myDelay =70;

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
            myhandler.postDelayed(runnable,myDelay);
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


    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void animateBubbles(){
        myhandler.removeCallbacks(runnable);
        myhandler.postDelayed(runnable, myDelay);
    }

    public void stopAnimation(){
        myhandler.removeCallbacks(runnable);
    }

    public SocialMediaAnimation(Context context) {
        super(context);
    }

    public SocialMediaAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
