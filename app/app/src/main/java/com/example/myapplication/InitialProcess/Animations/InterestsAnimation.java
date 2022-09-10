package com.example.myapplication.InitialProcess.Animations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import com.google.android.material.button.MaterialButton;

@SuppressLint("AppCompatCustomView")
public class InterestsAnimation extends MaterialButton {

    /*
        this class is responsible for providing the animation for the InterestsFragment

     */

    private final Handler myHandlerClick = new Handler();
    private long myDelay =5;
    private int minX =0;
    private int maxX =0;
    private int minY=0;
    private int maxY=0;
    private int xSpeed=0;
    private int ySpeed=0;
    private final int spacing = 330;

    // Animation wen geklickt wird
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
                if(getX()>=minX && getX()<=maxX){
                    if (!(getY() >= minY) || !(getY() <= maxY)) {
                        ySpeed = (ySpeed * (-1));
                    }
                }
                else{
                    xSpeed=(xSpeed*(-1));
                }
                setX(getX()+xSpeed);
                setY(getY()+ySpeed);
                myHandlerClick.postDelayed(runnable,myDelay);
                myDelay++;
        }
    };

    public void animateInterestsClick(){
        myHandlerClick.removeCallbacks(runnable);
        myHandlerClick.postDelayed(runnable, myDelay);
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

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed =  ySpeed;
    }

    public InterestsAnimation(Context context) {
        super(context);
    }

    public InterestsAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
