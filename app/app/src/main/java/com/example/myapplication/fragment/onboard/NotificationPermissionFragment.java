package com.example.myapplication.fragment.onboard;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;


public class NotificationPermissionFragment extends Fragment {

    /*
    This fragment shows the text of the permissions needed to send notifications
     */



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initImage(View view) {
        ImageView imageView = view.findViewById(R.id.imageViewNotification);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = view.getResources()
                .getDrawable(R.drawable.notification, view.getContext().getTheme());
        drawable.setTint(androidx.constraintlayout.widget.R.attr.colorAccent);
        imageView.setImageDrawable(drawable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifiaction_fragement, container, false);
        initImage(view);
        return view;
    }
}