package com.example.myapplication.FragementsUI;

import static com.example.myapplication.R.drawable.customyesbutton;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.TypriwriterAnimation;


public class InteressenFragement extends Fragment {

    private long animationDelay = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initButtons(View view) {
        Button buttonPolitik= view.findViewById(R.id.buttonKategoriePolitik);
        buttonPolitik.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                buttonPolitik.setBackground(getResources().getDrawable(customyesbutton));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_interessen_fragement, container, false);
        initButtons(view);
        initAnimation(view);
        return view;
    }

    private void initAnimation(View view){
        TypriwriterAnimation typriwriterAnimation = view.findViewById(R.id.socialMediaHeadline);
        typriwriterAnimation.setText("");
        typriwriterAnimation.setCharacterDelay(animationDelay);
        typriwriterAnimation.animateText(getResources().getString(R.string.interestsHeadline));
    }
}