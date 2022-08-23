package com.example.myapplication.FragementsUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.TypriwriterAnimation;


public class SocialMediaFragement extends Fragment {


    private long animationDelay = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_media_fragement, container, false);
        initAnimation(view);
        return view;
    }
    private void initAnimation(View view){
        TypriwriterAnimation typriwriterAnimation = view.findViewById(R.id.socialMediaConnectHeadline);
        typriwriterAnimation.setText("");
        typriwriterAnimation.setCharacterDelay(animationDelay);
        typriwriterAnimation.animateText(getResources().getString(R.string.socialMediaHeadline));
    }
}