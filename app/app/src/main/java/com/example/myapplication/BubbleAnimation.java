package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.os.Handler;
import android.widget.ImageButton;


public class BubbleAnimation extends androidx.appcompat.widget.AppCompatImageButton {

    private Handler myhandler  = new Handler();
    private int minX =0;
    private int maxX =0;
    private int minY =0;
    private int maxY =0;
    private int xSpeed=2;
    private int ySpeed=4;
    private long myDelay =50;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(getX()>=minX && getX()<=maxX){
                if(getY()>=minY && getY()<=maxY){
                    setX(getX()+xSpeed);
                    setY(getY()+ySpeed);
                }
                else{
                    ySpeed=ySpeed*(-1);
                    setX(getX()+xSpeed);
                    setY(getY()+ySpeed);
                }
            }
            else{
                xSpeed=xSpeed*(-1);
                setX(getX()+xSpeed);
                setY(getY()+ySpeed);
            }
           myhandler.postDelayed(runnable,myDelay);
        }
    };

    public void animateBubbles(){
        myhandler.removeCallbacks(runnable);
        myhandler.postDelayed(runnable, myDelay);
    }

    public void setY(int min,int max){
        minY= min;
        maxY = max;
    }

    public void setX(int min, int max){
        minX = min;
        maxX = max;
    }

    public BubbleAnimation(Context context) {
        super(context);
    }

    public BubbleAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
