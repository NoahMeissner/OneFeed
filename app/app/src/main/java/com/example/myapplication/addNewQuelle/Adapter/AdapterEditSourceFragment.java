package com.example.myapplication.addNewQuelle.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.SourceAdd;

import java.util.ArrayList;

public class AdapterEditSourceFragment extends
        RecyclerView.Adapter<ViewHolderEditSourceFragment>{

    private ArrayList<SourceAdd> categories;
    private final SourceSettingsChanged sourceSettingsChanged;

    public interface SourceSettingsChanged {
        void changedSource(SourceAdd source);
    }


    public AdapterEditSourceFragment(ArrayList<SourceAdd> categories, SourceSettingsChanged qSC){
        this.categories = categories;
        this.sourceSettingsChanged = qSC;
    }

    @NonNull
    @Override
    public ViewHolderEditSourceFragment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_quellen_icon,parent,false);

        return new ViewHolderEditSourceFragment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEditSourceFragment holder, int position) {
        holder.bind(categories.get(position), sourceSettingsChanged,categories.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(ArrayList<SourceAdd> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
