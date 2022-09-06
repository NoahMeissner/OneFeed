package com.example.myapplication.addNewQuelle.Fragement;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Quellen;

public class DeleteSourceFragement extends DialogFragment {



    private Quellen quellen;
    private InputDeleteSourceFragement inputDeleteSourceFragement;

    public interface InputDeleteSourceFragement{
        void inputDeleteSource(boolean result,Quellen quellen);
    }

    public DeleteSourceFragement(InputDeleteSourceFragement inputDeleteSourceFragement){
        this.inputDeleteSourceFragement = inputDeleteSourceFragement;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.delete_source_fragement, container, false);
        initUI(view);
        initButtons(view);
        return view;
    }

    private void initButtons(View view) {
        Button buttonNo = view.findViewById(R.id.buttonND);
        Button buttonYes = view.findViewById(R.id.buttonYD);
        buttonNo.setOnClickListener(view1 -> {
            inputDeleteSourceFragement.inputDeleteSource(false,quellen);
            onStop();
        });
        buttonYes.setOnClickListener(view1 -> {
            inputDeleteSourceFragement.inputDeleteSource(true,quellen);
            onStop();
        });
    }

    private void initUI(View view) {
        TextView textView = view.findViewById(R.id.deleteSourceHeadline);
        ImageView imageView = view.findViewById(R.id.deleteSourceImage);
        textView.setText(quellen.getName());
        imageView.setImageDrawable(quellen.getImage());
    }

    public void setQuellen(Quellen quellen) {
        this.quellen = quellen;
    }
}
