package com.example.myapplication.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AdapterEditSourceFragment;
import com.example.myapplication.data.addSource.Categories;
import com.example.myapplication.data.addSource.SourceAdd;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Objects;

public class ViewHolderEditSourceFragment extends RecyclerView.ViewHolder{

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private final MaterialSwitch aSwitch;
    private final TextView textView;

    public ViewHolderEditSourceFragment(@NonNull View itemView) {
        super(itemView);
        aSwitch = itemView.findViewById(R.id.editQuellenIconSwitch);
        textView = itemView.findViewById(R.id.editQuellenIconText);
    }

    public void bind(SourceAdd source, AdapterEditSourceFragment.SourceSettingsChanged sourceSettingsChanged, int size) {
        //@TODO Bug wen nur ein Element in Reihe Add Button zeigt nicht das richtige an
        //@TODO switch bearbeiten switch eintrag zu speichern
        if(size<=1 && !Objects.equals(source.getName(), Categories.ADDButton.name())){
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
