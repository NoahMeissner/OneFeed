package com.example.myapplication.animations;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Handler;


public class TypriwriterAnimation extends androidx.appcompat.widget.AppCompatTextView {

    private CharSequence myText;
    private int myIndex;
    private long myDelay =150;
    private final Handler myHandler = new Handler();


    public TypriwriterAnimation(@NonNull Context context) {
        super(context);
    }

    public TypriwriterAnimation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private final Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(myText.subSequence(0,myIndex++));
            if(myIndex<=myText.length() ){
                myHandler.postDelayed(characterAdder, myDelay);
            }
        }
    };

    public void animateText(CharSequence myTxt){
        myText = myTxt;
        myIndex =0;
        setText("");
        myHandler.removeCallbacks(characterAdder);
        myHandler.postDelayed(characterAdder, myDelay);
    }

    public void setCharacterDelay(long m){
        myDelay = m ;
    }
}
