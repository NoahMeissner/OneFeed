package com.example.myapplication.fragment.analysis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.FeedActivity;
import com.example.myapplication.data.addSource.Category;

import java.util.Objects;

public class PermissionsDialogFragement extends DialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_consumption_permission, container, false);
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initButtons(view);
        return view;
    }

    private void initButtons(View view) {
        Button buttonYes = view.findViewById(R.id.buttonYPermisson);
        Button buttonNo = view.findViewById(R.id.buttonNPermission);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSharedPreferences();
                onStop();
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FeedActivity.class);
                startActivity(intent);
                onStop();
            }
        });
    }

    private void editSharedPreferences() {
        SharedPreferences pref = getContext().getSharedPreferences(getResources()
                .getString(R.string.initProcesBoolean), 0);
        SharedPreferences.Editor editPreferences = pref.edit();
        editPreferences.putBoolean(Category.initial.Consumptionanalyse.name(), true);
    }
}
