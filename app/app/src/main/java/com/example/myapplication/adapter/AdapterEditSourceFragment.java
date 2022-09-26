package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.viewholder.ViewHolderEditSourceFragment;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;

public class AdapterEditSourceFragment extends
        RecyclerView.Adapter<ViewHolderEditSourceFragment>{

    private ArrayList<SourceAdd> source;
    private final SourceSettingsChanged sourceSettingsChanged;
    private final String name;

    public interface SourceSettingsChanged {
        void changedSource(SourceAdd source);
    }


    public AdapterEditSourceFragment(ArrayList<SourceAdd> categories,String name, SourceSettingsChanged qSC){
        this.source = categories;
        this.name = name;
        this.sourceSettingsChanged = qSC;
    }

    @NonNull
    @Override
    public ViewHolderEditSourceFragment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_edit_quellen_icon,parent,false);

        return new ViewHolderEditSourceFragment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEditSourceFragment holder, int position) {
        holder.bind(source.get(position), sourceSettingsChanged, name);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSource(ArrayList<SourceAdd> source) {
        this.source = source;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return source.size();
    }
}
