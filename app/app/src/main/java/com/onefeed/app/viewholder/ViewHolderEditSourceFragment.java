package com.onefeed.app.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onefeed.app.R;
import com.onefeed.app.adapter.AdapterEditSourceFragment;
import com.onefeed.app.data.addSource.Constants;
import com.onefeed.app.data.addSource.SourceAdd;
import com.google.android.material.materialswitch.MaterialSwitch;

public class ViewHolderEditSourceFragment extends RecyclerView.ViewHolder{

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private final MaterialSwitch aSwitch;
    private final TextView textView;

    public ViewHolderEditSourceFragment(@NonNull View itemView) {
        super(itemView);
        aSwitch = itemView.findViewById(R.id.edit_source_icon_switch);
        textView = itemView.findViewById(R.id.edit_source_icon_text);
    }

    public void bind(SourceAdd source,
                     AdapterEditSourceFragment.SourceSettingsChanged sourceSettingsChanged,
                     String name) {

        if(!name.equals(Constants.ADDButton.name())){
            String setNotification = "Notification";
            textView.setText(setNotification);
                aSwitch.setChecked(source.isNotification());
                aSwitch.setOnClickListener(view -> sourceSettingsChanged.changedSource(source));
                return;
        }
        textView.setText(source.getName());
        aSwitch.setChecked(source.isEnabled());
        aSwitch.setOnClickListener(view -> sourceSettingsChanged.changedSource(source));
    }
}
