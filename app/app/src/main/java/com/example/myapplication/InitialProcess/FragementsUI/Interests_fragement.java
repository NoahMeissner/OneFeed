package com.example.myapplication.InitialProcess.FragementsUI;

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

import com.example.myapplication.InitialProcess.Animations.InteressenAnimation;
import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Categories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Interests_fragement extends Fragment {

    /*
    This method is responsible for querying interests
     */

    private final Point size = new Point();
    private final HashMap<String,InteressenAnimation> buttons = new HashMap<>();
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
                String.valueOf(Categories.interests.Politik),
                view.findViewById(R.id.buttonKategoriePolitik));

        buttons.put(
                String.valueOf(Categories.interests.Corona),
                view.findViewById(R.id.buttonKategorieCorona));

        buttons.put(
                String.valueOf(Categories.interests.Gaming),
                view.findViewById(R.id.buttonKategorieGaming));

        buttons.put(String.valueOf(
                Categories.interests.Technik),
                view.findViewById(R.id.buttonKategorieTechnik));

        buttons.put(
                String.valueOf(Categories.interests.Wirtschaft),
                view.findViewById(R.id.buttonKategorieWirtschaft));

        buttons.put(
                String.valueOf(Categories.interests.Sport),
                view.findViewById(R.id.buttonKategorieSport));

        for(String s:buttons.keySet()){
            setListener(Objects.requireNonNull(buttons.get(s)),s);
        }
    }

    // This method sets the parameters for the animation, which is implemented in the Interests Animation class
    private void setAnimation(InteressenAnimation interessenAnimation, float x, float y){
        int speedX =1;
        int speedY =1;
        interessenAnimation.setDelay(10);
        WindowManager windowManager = requireActivity().getWindowManager();
        windowManager.getDefaultDisplay().getSize(size);
        interessenAnimation.setXField(0,size.x);
        interessenAnimation.setYField(0,size.y);
        if(x<=interessenAnimation.getX()){
            interessenAnimation.setxSpeed(speedX);
        }
        if(x>interessenAnimation.getX()){
            interessenAnimation.setxSpeed(-speedX);
        }
        if(y<=interessenAnimation.getY()){
            interessenAnimation.setySpeed(speedY);
        }
        if(y>interessenAnimation.getY()){
            interessenAnimation.setySpeed(-speedY);
        }
        interessenAnimation.animateInteressenClick();
    }

    //This method sets a listener on all buttons in the Fragement, to receive the user's responses
    private void setListener(InteressenAnimation interessenAnimation,String kategorie){
        interessenAnimation.setOnClickListener(view -> {
            if(buttonSize==0){
                buttonSize=interessenAnimation.getHeight();
            }
            if(interessenAnimation.getHeight()>=buttonSize){
                buttonAnimationReset(interessenAnimation,kategorie);
            }
            if(interessenAnimation.getHeight()==buttonSize){
                detecButtonAnimation(interessenAnimation,kategorie);
            }
        });
    }

    //This method rolls back a user's answer if he revises it
    @SuppressLint("UseCompatLoadingForDrawables")
    private void buttonAnimationReset(InteressenAnimation interessenAnimation,String kategorie) {
        interessenAnimation.getLayoutParams().width= buttonSize;
        interessenAnimation.getLayoutParams().height= buttonSize;
        interessenAnimation.setBackground(getResources()
                .getDrawable(R.drawable.ovalbutton, requireActivity().getTheme()));

        interessenAnimation.setTextColor(getResources()
                .getColor(R.color.black, requireActivity().getTheme()));

        results.remove(kategorie);
        dataPasser.onDataPass(results);
    }

    //This method recognizes a reaction of the user and makes it visible in the layout and saves his answer in an ArrayList
    @SuppressLint("UseCompatLoadingForDrawables")
    private void detecButtonAnimation(InteressenAnimation interessenAnimation, String kategorie){
        double magnificationfactor = 1.1;
        interessenAnimation.getLayoutParams().width= (int) (interessenAnimation.getWidth()*magnificationfactor);
        interessenAnimation.getLayoutParams().height= (int) (interessenAnimation.getHeight()*magnificationfactor);
        interessenAnimation.setTextColor(getResources().getColor(R.color.black, requireActivity().getTheme()));

        interessenAnimation.setBackground(getResources()
                .getDrawable(R.drawable.customyesbutton, requireActivity().getTheme()));

        results.add(kategorie);
        dataPasser.onDataPass(results);
        for(String s:buttons.keySet()){
            if(!Objects.equals(s, kategorie)){
                setAnimation(Objects.requireNonNull(buttons.get(s)),interessenAnimation.getX(),interessenAnimation.getY());
            }
        }
    }

    // This interface allows the activity to transmit the information
    public interface OnDataPass{
         void onDataPass(ArrayList<String> interessenList);
    }
}

