package com.onefeed.app.fragment.addSource;

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

import com.onefeed.app.R;
import com.onefeed.app.data.addSource.SourceAdd;

import java.util.Objects;

public class DeleteSourceFragment extends DialogFragment {

    /*
    This Method will display a Delete Source Fragment which give the User after one Long Click
    the possibility to delete one Source
     */

    /*
    Constants which will set by the Constructor
     */
    // This is the source Add Object which was selected
    private final SourceAdd source;
    // Interface which hand over the decision of the User
    private final DeleteSourceFragmentInterface deleteSFInterface;

    /*
    Constructor
     */
    public DeleteSourceFragment(DeleteSourceFragmentInterface inputDeleteSourceFragment,
                                SourceAdd source){
        this.deleteSFInterface = inputDeleteSourceFragment;
        this.source = source;
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
        // Declare one View Element
        View view =inflater.inflate(R.layout.fragement_delete_source,
                container,
                false);
        // Set The Background of the Fragment transparent
        Objects.requireNonNull(getDialog())
                .getWindow()
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initUI(view);
        initButtons(view);
        return view;
    }

    /*
    This Method will initialise the Buttons, to accept the deleting of one Source Object
     */
    private void initButtons(View view) {
        Button buttonNo = view.findViewById(R.id.button_no_delete);
        Button buttonYes = view.findViewById(R.id.button_yes_delete);
        buttonNo.setOnClickListener(view1 -> {
            deleteSFInterface.deleteSourceFromLongClick(false, source);
            onStop();
        });
        buttonYes.setOnClickListener(view1 -> {
            deleteSFInterface.deleteSourceFromLongClick(true, source);
            onStop();
        });
    }

    /*
    This Method initial all UI Elements
     */
    private void initUI(View view) {
        TextView textView = view.findViewById(R.id.deleteSourceHeadline);
        ImageView imageView = view.findViewById(R.id.delete_source_image);
        textView.setText(source.getName());
        imageView.setImageResource(source.getImageRessourceID());
    }

    /*
    Interface which helps to detect removed Sources
     */
    public interface DeleteSourceFragmentInterface {
        void deleteSourceFromLongClick(boolean result, SourceAdd source);
    }
}
