package com.example.myapplication.FragementsUI;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.example.myapplication.BubbleAnimation;
import com.example.myapplication.R;

public class SocialMediaFragement extends Fragment {

    private final Point size = new Point();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_media_fragement, container, false);
        initAnimation(view,R.id.imageRedditButton);
        initAnimation(view,R.id.imageTwitterButton);
        return view;
    }


    private void initAnimation(View view, int id){
        WindowManager windowManager = getActivity().getWindowManager();
        windowManager.getDefaultDisplay().getSize(size);
        BubbleAnimation bubbleAnimation = view.findViewById(id);
        bubbleAnimation.setOnClickListener(view1 -> {
            bubbleAnimation.setxSpeed(0);
            bubbleAnimation.setySpeed(0);
            bubbleAnimation.setBackground(getResources().getDrawable(R.drawable.customyesbutton, getActivity().getTheme()));
        });
        //@TODO Magic numbers weg
        bubbleAnimation.setX(0,size.x-330);
        bubbleAnimation.setY(330,size.y-660);
        bubbleAnimation.animateBubbles();
    }
}