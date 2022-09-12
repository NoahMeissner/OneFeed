package com.example.myapplication.fragment.addSource;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.Objects;

public class DeleteSourceFragment extends DialogFragment {



    private SourceAdd source;
    private final InputDeleteSourceFragment inputDeleteSourceFragment;

    public interface InputDeleteSourceFragment {
        void inputDeleteSource(boolean result, SourceAdd source);
    }

    public DeleteSourceFragment(InputDeleteSourceFragment inputDeleteSourceFragment){
        this.inputDeleteSourceFragment = inputDeleteSourceFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.delete_source_fragement, container, false);
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initUI(view);
        initButtons(view);
        return view;
    }

    private void initButtons(View view) {
        Button buttonNo = view.findViewById(R.id.buttonND);
        Button buttonYes = view.findViewById(R.id.buttonYD);
        buttonNo.setOnClickListener(view1 -> {
            inputDeleteSourceFragment.inputDeleteSource(false, source);
            onStop();
        });
        buttonYes.setOnClickListener(view1 -> {
            inputDeleteSourceFragment.inputDeleteSource(true, source);
            onStop();
        });
    }

    private void initUI(View view) {
        TextView textView = view.findViewById(R.id.deleteSourceHeadline);
        ImageView imageView = view.findViewById(R.id.deleteSourceImage);
        textView.setText(source.getName());
        imageView.setImageDrawable(source.getImage());
    }

    public void setSource(SourceAdd source) {
        this.source = source;
    }
}
