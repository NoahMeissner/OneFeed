package com.example.myapplication.FragementsUI;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.example.myapplication.animations.InteressenAnimation;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class InteressenFragement extends Fragment {

    private final Point size = new Point();
    private final HashMap<String,InteressenAnimation> buttons = new HashMap<>();
    private final ArrayList<String> results = new ArrayList<>();
    private int buttonSize=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_interessen_fragement, container, false);
        initButtons(view);
        return view;
    }

    private void initButtons(View view){
        buttons.put("Politik",view.findViewById(R.id.buttonKategoriePolitik));
        buttons.put("Corona",view.findViewById(R.id.buttonKategorieCorona));
        buttons.put("Gaming",view.findViewById(R.id.buttonKategorieGaming));
        buttons.put("Technik",view.findViewById(R.id.buttonKategorieTechnik));
        buttons.put("Wirtschaft",view.findViewById(R.id.buttonKategorieWirtschaft));
        buttons.put("Sport",view.findViewById(R.id.buttonKategorieSport));
        for(String s:buttons.keySet()){
            setListener(Objects.requireNonNull(buttons.get(s)),s);
        }
    }

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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void buttonAnimationReset(InteressenAnimation interessenAnimation,String kategorie) {
        interessenAnimation.getLayoutParams().width= buttonSize;
        interessenAnimation.getLayoutParams().height= buttonSize;
        interessenAnimation.setBackground(getResources().getDrawable(R.drawable.ovalbutton, requireActivity().getTheme()));
        interessenAnimation.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
        results.remove(kategorie);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void detecButtonAnimation(InteressenAnimation interessenAnimation, String kategorie){
        interessenAnimation.getLayoutParams().width= (int) (interessenAnimation.getWidth()*1.1);
        interessenAnimation.getLayoutParams().height= (int) (interessenAnimation.getHeight()*1.1);
        interessenAnimation.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
        interessenAnimation.setBackground(getResources().getDrawable(R.drawable.customyesbutton, requireActivity().getTheme()));
        results.add(kategorie);
        for(String s:buttons.keySet()){
            if(!Objects.equals(s, kategorie)){
                setAnimation(Objects.requireNonNull(buttons.get(s)),interessenAnimation.getX(),interessenAnimation.getY());
            }
        }
    }

    public ArrayList<String> getResults() {
        return results;
    }
}