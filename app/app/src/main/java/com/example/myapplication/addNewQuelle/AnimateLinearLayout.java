package com.example.myapplication.addNewQuelle;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class AnimateLinearLayout extends FrameLayout {

    private int rotation = 2;
    private long myDelay =80;
    private final Handler myHandler = new Handler();




    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setRotation(rotation);
            myHandler.postDelayed(runnable,myDelay);
            rotation=rotation*(-1);
        }
    };

    public void animateText(){

        myHandler.removeCallbacks(runnable);
        myHandler.postDelayed(runnable, myDelay);
    }

    //@TODO Eine Methode Stop Animation implementieren
    public void stopAnimation(){
        myHandler.removeCallbacksAndMessages(null);

    }

    public void setCharacterDelay(long m){
        myDelay = m ;
    }

    public AnimateLinearLayout(Context context) {
        super(context);
    }

    public AnimateLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
