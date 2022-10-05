package com.onefeed.app.fragment.onboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onefeed.app.R;
import com.onefeed.app.animation.onboard.TypewriterAnimation;


public class WelcomeFragment extends Fragment {


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
        TypewriterAnimation typewriterAnimation = view.findViewById(R.id.welcomeText);
        typewriterAnimation.animateText(getResources().getString(R.string.welcome));
    }
}