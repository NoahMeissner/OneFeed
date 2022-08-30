package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

public class InteressenAnimation extends androidx.appcompat.widget.AppCompatButton {

    private final Handler myhandler  = new Handler();
    private final long myDelay =50;
    private int minX =0;
    private int maxX =0;
    private int minY=0;
    private int maxY=0;
    private int xSpeed=0;
    private int ySpeed=0;
    private float startX=getX();
    private float startY=getY();


    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("x", String.valueOf(startX));
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

    @Override
    public float getX() {
        return super.getX();
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void animateInteressen(){
        myhandler.removeCallbacks(runnable);
        myhandler.postDelayed(runnable, myDelay);
    }




    public InteressenAnimation(Context context) {
        super(context);
    }

    public InteressenAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
