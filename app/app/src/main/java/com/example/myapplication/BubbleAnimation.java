package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.os.Handler;


@SuppressLint("ViewConstructor")
public class BubbleAnimation extends androidx.appcompat.widget.AppCompatImageButton {

    private final Handler myhandler  = new Handler();
    private int minX =0;
    private int maxX =0;
    private int minY=0;
    private int maxY=0;
    private int xSpeed=2;
    private int ySpeed= (int) (2*xSpeed*Math.random());
    private final long myDelay =50;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(getX()>=minX && getX()<=maxX){
                if (!(getY() >= minY) || !(getY() <= maxY)) {
                    ySpeed = (int) (ySpeed * (-1)*Math.random());
                }
            }
            else{
                xSpeed= (int) (xSpeed*(-1));
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

    public BubbleAnimation(Context context) {
        super(context);
    }

    public BubbleAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
