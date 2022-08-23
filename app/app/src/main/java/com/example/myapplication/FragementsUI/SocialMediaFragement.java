package com.example.myapplication.FragementsUI;

import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.myapplication.BubbleAnimation;
import com.example.myapplication.R;
import com.example.myapplication.TypriwriterAnimation;


public class SocialMediaFragement extends Fragment {


    private long animationDelay = 150;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_media_fragement, container, false);
        initAnimation(view,R.id.imageTwitterButton);
        initAnimation(view,R.id.imageRedditButton);
        return view;
    }
    private void initAnimation(View view, int id){
        WindowManager w = getActivity().getWindowManager();
        Point size = new Point();
        w.getDefaultDisplay().getSize(size);
        BubbleAnimation bubbleAnimation = view.findViewById(id);
        bubbleAnimation.setX(0,size.x-330);
        bubbleAnimation.setY(330,size.y-660);
        bubbleAnimation.animateBubbles();



    }
}