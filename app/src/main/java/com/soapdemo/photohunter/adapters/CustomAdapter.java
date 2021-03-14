package com.soapdemo.photohunter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soapdemo.photohunter.R;
import com.soapdemo.photohunter.databinding.TextRowItemBinding;

import java.util.List;

public class CustomAdapter<T> extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<T> mDataSet;

    public CustomAdapter(List<T> dataSet) {
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
        assert binding != null;
        binding.setContent(  (String)this.mDataSet.get(position));
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return this.mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
