package com.onefeed.app.fragment.feed;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.onefeed.app.R;

import java.util.Objects;

public class ErrorFragment extends DialogFragment {

    /*
    This Fragment Displays the User that the Internet Connection is not working.
     */
    // Listener to check if button was pressed
    private NewTry newTry;

    /*
    Constructor
     */
    public ErrorFragment(){
    }

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
            initUI(view);
            return view;
        }

        public void setListener(NewTry listener) {
            this.newTry = listener;
        }

        /*
        This Method initialise the important UI Parts
         */
    private void initUI(View view) {
        ProgressBar progressBar = view.findViewById(R.id.loadingPanelError);
        progressBar.setVisibility(View.GONE);
        initButton(view,progressBar);

    }

    /*
    This Method will initialise the Buttons
     */
    private void initButton(View view, ProgressBar progressBar) {
            Button button = view.findViewById(R.id.errorButton);
            button.setOnClickListener(view1 -> {
                progressBar.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                newTry.buttonHasPressed(true);
                this.dismiss();
            });
    }
    
    public interface NewTry {
            void buttonHasPressed(boolean boo);
    }
}
