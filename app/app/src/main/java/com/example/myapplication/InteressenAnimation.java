package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

public class InteressenAnimation extends androidx.appcompat.widget.AppCompatButton {

    private final Handler myhandlerClick  = new Handler();
    private long myDelay =5;
    private int minX =0;
    private int maxX =0;
    private int minY=0;
    private int maxY=0;
    private int xSpeed=0;
    private int ySpeed=0;
    private final int spacing = 330;

    // Animation wenn geklickt wird
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
                if(getX()>=minX && getX()<=maxX){
                    if (!(getY() >= minY) || !(getY() <= maxY)) {
                        ySpeed = (int) (ySpeed * (-1));
                    }
                }
                else{
                    xSpeed=(xSpeed*(-1));
                }
                setX(getX()+xSpeed);
                setY(getY()+ySpeed);
                myhandlerClick.postDelayed(runnable,myDelay);
                myDelay++;
        }
    };

    public void animateInteressenClick(){
        myhandlerClick.removeCallbacks(runnable);
        myhandlerClick.postDelayed(runnable, myDelay);
    }

    public void setDelay(long myDelay){
        this.myDelay = myDelay;
    }

    public void setXField(int minX, int maxX) {
        this.minX = minX;
        this.maxX = maxX-spacing;
    }

    public void setYField(int minY, int maxY) {
        this.minY = minY+spacing;
        this.maxY = maxY-2*spacing;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed =  ySpeed;
    }

    public InteressenAnimation(Context context) {
        super(context);
    }

    public InteressenAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
