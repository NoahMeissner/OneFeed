package com.example.myapplication.addNewQuelle.Adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ViewholderEditQuellenFragement extends RecyclerView.ViewHolder{

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private final Switch aSwitch;
    private final TextView textView;

    public ViewholderEditQuellenFragement(@NonNull View itemView) {
        super(itemView);
        aSwitch = itemView.findViewById(R.id.editQuellenIconSwitch);
        textView = itemView.findViewById(R.id.editQuellenIconText);
    }

    public void bind(String[] strings) {
        textView.setText(strings[0]);
        aSwitch.setChecked(Boolean.parseBoolean(strings[1]));
        aSwitch.setOnClickListener(view -> System.out.println("Hello"));
    }
}
