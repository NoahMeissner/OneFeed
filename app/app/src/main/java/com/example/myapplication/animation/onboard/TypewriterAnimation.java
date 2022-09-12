package com.example.myapplication.animation.onboard;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Handler;


public class TypewriterAnimation extends androidx.appcompat.widget.AppCompatTextView {

    /*
        this class is responsible for providing the animation for the Welcome fragment
     */

    // Constants
    private CharSequence myText;
    private int myIndex;
    private long myDelay = 150;
    private final Handler myHandler = new Handler();


    public TypewriterAnimation(@NonNull Context context) {
        super(context);
    }

    public TypewriterAnimation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*
   runnable to outsource the calculation of the animation into a new thread
    */
    private final Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            /*
            method sets the string by one letter at a time and
             is passed as long as the text is smaller than the end text.
             */
            setText(myText.subSequence(0,myIndex++));
            if(myIndex<=myText.length() ){
                myHandler.postDelayed(characterAdder, myDelay);
            }
        }
    };

    public void animateText(CharSequence myTxt){
        myText = myTxt;
        myIndex = 0;
        setText("");
        myHandler.removeCallbacks(characterAdder);
        myHandler.postDelayed(characterAdder, myDelay);
    }
}
