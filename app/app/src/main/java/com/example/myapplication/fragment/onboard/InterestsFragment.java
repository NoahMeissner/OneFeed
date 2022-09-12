package com.example.myapplication.fragment.onboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.myapplication.animation.onboard.InterestsAnimation;
import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class InterestsFragment extends Fragment {

    /*
    This method is responsible for querying interests
     */

    private final Point size = new Point();
    private final HashMap<String, InterestsAnimation> buttons = new HashMap<>();
    private final ArrayList<String> results = new ArrayList<>();
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
                String.valueOf(Category.interests.Politik),
                view.findViewById(R.id.buttonKategoriePolitik));

        buttons.put(
                String.valueOf(Category.interests.Corona),
                view.findViewById(R.id.buttonKategorieCorona));

        buttons.put(
                String.valueOf(Category.interests.Gaming),
                view.findViewById(R.id.buttonKategorieGaming));

        buttons.put(String.valueOf(
                Category.interests.Technik),
                view.findViewById(R.id.buttonKategorieTechnik));

        buttons.put(
                String.valueOf(Category.interests.Wirtschaft),
                view.findViewById(R.id.buttonKategorieWirtschaft));

        buttons.put(
                String.valueOf(Category.interests.Sport),
                view.findViewById(R.id.buttonKategorieSport));

        for(String s:buttons.keySet()){
            setListener(Objects.requireNonNull(buttons.get(s)),s);
        }
    }

    // This method sets the parameters for the animation, which is implemented in the Interests Animation class
    private void setAnimation(InterestsAnimation interestsAnimation, float x, float y){
        int speedX =1;
        int speedY =1;
        interestsAnimation.setDelay(10);
        WindowManager windowManager = requireActivity().getWindowManager();
        windowManager.getDefaultDisplay().getSize(size);
        interestsAnimation.setXField(0,size.x);
        interestsAnimation.setYField(0,size.y);
        if(x<=interestsAnimation.getX()){
            interestsAnimation.setXSpeed(speedX);
        }
        if(x>interestsAnimation.getX()){
            interestsAnimation.setXSpeed(-speedX);
        }
        if(y<=interestsAnimation.getY()){
            interestsAnimation.setYSpeed(speedY);
        }
        if(y>interestsAnimation.getY()){
            interestsAnimation.setYSpeed(-speedY);
        }
        interestsAnimation.animateInterestsClick();
    }

    //This method sets a listener on all buttons in the Fragment, to receive the user's responses
    private void setListener(InterestsAnimation interestsAnimation, String category){
        interestsAnimation.setOnClickListener(view -> {
            if(buttonSize==0){
                buttonSize=interestsAnimation.getHeight();
            }
            if(interestsAnimation.getHeight()>=buttonSize){
                buttonAnimationReset(interestsAnimation,category);
            }
            if(interestsAnimation.getHeight()==buttonSize){
                detecButtonAnimation(interestsAnimation,category);
            }
        });
    }

    //This method rolls back a user's answer if he revises it
    @SuppressLint("UseCompatLoadingForDrawables")
    private void buttonAnimationReset(InterestsAnimation interestsAnimation, String category) {
        interestsAnimation.getLayoutParams().width= buttonSize;
        interestsAnimation.getLayoutParams().height= buttonSize;
        interestsAnimation.setBackground(getResources()
                .getDrawable(R.drawable.ovalbutton, requireActivity().getTheme()));

        interestsAnimation.setTextColor(getResources()
                .getColor(R.color.black, requireActivity().getTheme()));

        results.remove(category);
        dataPasser.onDataPass(results);
    }

    //This method recognizes a reaction of the user and makes it visible in the layout and saves his answer in an ArrayList
    @SuppressLint("UseCompatLoadingForDrawables")
    private void detecButtonAnimation(InterestsAnimation interestsAnimation, String category){
        double magnificationFactor = 1.1;
        interestsAnimation.getLayoutParams().width= (int) (interestsAnimation.getWidth()*magnificationFactor);
        interestsAnimation.getLayoutParams().height= (int) (interestsAnimation.getHeight()*magnificationFactor);
        interestsAnimation.setTextColor(getResources().getColor(R.color.black, requireActivity().getTheme()));

        interestsAnimation.setBackground(getResources()
                .getDrawable(R.drawable.customyesbutton, requireActivity().getTheme()));

        results.add(category);
        dataPasser.onDataPass(results);
        for(String s:buttons.keySet()){
            if(!Objects.equals(s, category)){
                setAnimation(Objects.requireNonNull(buttons.get(s)),interestsAnimation.getX(),interestsAnimation.getY());
            }
        }
    }

    // This interface allows the activity to transmit the information
    public interface OnDataPass{
         void onDataPass(ArrayList<String> interestsList);
    }
}

