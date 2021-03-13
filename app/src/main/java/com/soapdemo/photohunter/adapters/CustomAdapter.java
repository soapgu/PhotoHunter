package com.soapdemo.photohunter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soapdemo.photohunter.R;
import com.soapdemo.photohunter.databinding.TextRowItemBinding;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private String[] mDataSet;

    public CustomAdapter(String[] dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                //.inflate(R.layout.text_row_item, parent, false);
        TextRowItemBinding binding  = DataBindingUtil.inflate(inflater,R.layout.text_row_item,parent, false );
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextRowItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setContent(  this.mDataSet[position] );
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return this.mDataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
