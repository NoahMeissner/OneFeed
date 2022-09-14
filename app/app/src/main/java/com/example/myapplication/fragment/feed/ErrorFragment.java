package com.example.myapplication.fragment.feed;

import android.annotation.SuppressLint;
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

import java.util.Objects;

public class ErrorFragment extends DialogFragment {

    /*
    This Fragment Displays the User that the Internet Connection is not working.
     */

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(
                @NonNull LayoutInflater inflater,
                @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_error_internet, container, false);
            Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            initButton(view);
            return view;
        }

    private void initButton(View view) {
            Button button = view.findViewById(R.id.errorButton);
            button.setOnClickListener(view1 -> {
                //@TODO refresh
                onStop();
            });
    }


}
