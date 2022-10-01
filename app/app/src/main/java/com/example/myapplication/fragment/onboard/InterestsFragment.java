package com.example.myapplication.fragment.onboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.myapplication.animation.onboard.InterestsAnimation;
import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class InterestsFragment extends Fragment {

    /*
    This method is responsible for querying interests
     */

    // Constants
    private final Point size = new Point();
    private final HashMap<Constants.interests, InterestsAnimation> buttons = new HashMap<>();
    private final ArrayList<Constants.interests> results = new ArrayList<>();
    private int buttonSize=0;
    private OnDataPass dataPasser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_interessen_fragement, container, false);
        initButtons(view);
        return view;
    }

    // This method initializes all buttons representing the interests
    private void initButtons(View view){
        buttons.put(
                Constants.interests.Politik,
                view.findViewById(R.id.buttonKategoriePolitik));

        buttons.put(
                Constants.interests.Corona,
                view.findViewById(R.id.buttonKategorieCorona));

        buttons.put(
                Constants.interests.Gaming,
                view.findViewById(R.id.buttonKategorieGaming));

        buttons.put(
                Constants.interests.Technik,
                view.findViewById(R.id.buttonKategorieTechnik));

        buttons.put(
                Constants.interests.Wirtschaft,
                view.findViewById(R.id.buttonKategorieWirtschaft));

        buttons.put(
                Constants.interests.Sport,
                view.findViewById(R.id.buttonKategorieSport));

        for(Constants.interests s:buttons.keySet()){
            setListener(Objects.requireNonNull(buttons.get(s)),s);
        }
    }

    /*
    This method sets the parameters for the animation,
     which is implemented in the Interests Animation class
     */
    private void setAnimation(InterestsAnimation interestsAnimation, float x, float y){
        /*
        The field size for the animation is transferred here
         */
        WindowManager windowManager = requireActivity().getWindowManager();
        windowManager.getDefaultDisplay().getSize(size);
        interestsAnimation.setXField(0,size.x);
        interestsAnimation.setYField(0,size.y);
        interestsAnimation.setMyDelay(requireActivity()
                .getResources()
                .getInteger(R.integer.delay));

        int xSpeed = 1;
        if(x<=interestsAnimation.getX()){
            interestsAnimation.setXSpeed(xSpeed);
        }
        if(x>interestsAnimation.getX()){
            interestsAnimation.setXSpeed(-xSpeed);
        }
        int ySpeed = 1;
        if(y<=interestsAnimation.getY()){
            interestsAnimation.setYSpeed(ySpeed);
        }
        if(y>interestsAnimation.getY()){
            interestsAnimation.setYSpeed(-ySpeed);
        }
        interestsAnimation.animateInterestsClick();
    }

    //This method sets a listener on all buttons in the Fragment, to receive the user's responses
    private void setListener(InterestsAnimation interestsAnimation, Constants.interests category){
        interestsAnimation.setOnClickListener(view -> {
            if(buttonSize==0){
                buttonSize=interestsAnimation.getHeight();
            }
            if(interestsAnimation.getHeight()>=buttonSize){
                buttonAnimationReset(interestsAnimation,category);
            }
            if(interestsAnimation.getHeight()==buttonSize){
                detectButtonAnimation(interestsAnimation,category);
            }
        });
    }

    //This method rolls back a user's answer if he revises it
    @SuppressLint("UseCompatLoadingForDrawables")
    private void buttonAnimationReset(InterestsAnimation interestsAnimation,
                                      Constants.interests category) {

        interestsAnimation.getLayoutParams().width= buttonSize;
        interestsAnimation.getLayoutParams().height= buttonSize;
        interestsAnimation.setBackground(getResources()
                .getDrawable(R.drawable.ovalbutton, requireActivity().getTheme()));

        interestsAnimation.setTextColor(getResources()
                .getColor(R.color.black, requireActivity().getTheme()));

        results.remove(category);
        dataPasser.onDataPass(results);
    }

    /*
    This method recognizes a reaction of the user and makes
     it visible in the layout and saves his answer in an ArrayList
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void detectButtonAnimation(InterestsAnimation interestsAnimation,
                                       Constants.interests category){

        double magnificationFactor = 1.1;
        interestsAnimation.getLayoutParams()
                .width= (int) (interestsAnimation.getWidth()*magnificationFactor);

        interestsAnimation.getLayoutParams()
                .height= (int) (interestsAnimation.getHeight()*magnificationFactor);

        interestsAnimation
                .setTextColor(getResources().getColor(R.color.black, requireActivity().getTheme()));

        interestsAnimation.setBackground(getResources()
                .getDrawable(R.drawable.customyesbutton, requireActivity().getTheme()));

        results.add(category);
        dataPasser.onDataPass(results);
        for(Constants.interests s:buttons.keySet()){
            if(!Objects.equals(s, category)){
                setAnimation(Objects.requireNonNull(buttons.get(s)),
                        interestsAnimation.getX(),interestsAnimation.getY());

                stopAnimation();
            }
        }
    }

    /*
    This Method stops the Animation
     */
    private void stopAnimation(){
        int secondsUntilStop = 4000;
        Runnable r = () -> {
            try {
                Thread.sleep(secondsUntilStop);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(Constants.interests interests : buttons.keySet()){
                Objects.requireNonNull(buttons.get(interests)).stopAnimation();
            }
            Log.d("InterestsFragment","Finish");
        };
        ExecutorService service = Executors.newScheduledThreadPool(1);
        service.execute(r);
    }



    // This interface allows the activity to transmit the information
    public interface OnDataPass{
         void onDataPass(ArrayList<Constants.interests> interestsList);
    }
}

