package com.example.myapplication.FragementsUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.TypriwriterAnimation;


public class WillkommenFragement extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_willkommen_fragement, container, false);
        initAnimation(view);
        return view;
    }

    private void initAnimation(View view){
        TypriwriterAnimation typriwriterAnimation = view.findViewById(R.id.welcomeText);
        typriwriterAnimation.setText("");
        long animationDelay = 150;
        typriwriterAnimation.setCharacterDelay(animationDelay);
        typriwriterAnimation.animateText(getResources().getString(R.string.willkommen));
    }
}