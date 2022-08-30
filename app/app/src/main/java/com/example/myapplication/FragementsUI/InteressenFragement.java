package com.example.myapplication.FragementsUI;

import static com.example.myapplication.R.drawable.customyesbutton;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.BubbleAnimation;
import com.example.myapplication.InteressenAnimation;
import com.example.myapplication.R;
import com.example.myapplication.TypriwriterAnimation;

import java.util.HashMap;


public class InteressenFragement extends Fragment {

    private InteressenAnimation buttonPolitik;
    private InteressenAnimation buttonCorona;
    private InteressenAnimation buttonGaming;
    private InteressenAnimation buttonTechnik;
    private InteressenAnimation buttonWirtschaft;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_interessen_fragement, container, false);
        initButtons(view);
        initAnimation(view);
        return view;
    }

    private void initAnimation(View view) {

        //HashMap<String,InteressenAnimation> buttons = new HashMap<>();
    }

    private void initButtons(View view){
        buttonPolitik = view.findViewById(R.id.buttonKategoriePolitik);
        buttonCorona = view.findViewById(R.id.buttonKategorieCorona);
        buttonGaming = view.findViewById(R.id.buttonKategorieGaming);
        buttonTechnik = view.findViewById(R.id.buttonKategorieTechnik);
        buttonWirtschaft= view.findViewById(R.id.buttonKategorieWirtschaft);
    }

    private void initialButtonAnimation(InteressenAnimation interessenAnimation, int speedx, int speedy){
        WindowManager windowManager = getActivity().getWindowManager();
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        interessenAnimation.setX(0,size.x-330);
        interessenAnimation.setY(330,size.y-660);
        interessenAnimation.setxSpeed(speedx);
        interessenAnimation.setySpeed(speedy);
        interessenAnimation.animateInteressen();
    }

    //private void test()








}