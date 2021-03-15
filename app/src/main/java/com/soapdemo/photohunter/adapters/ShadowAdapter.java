package com.soapdemo.photohunter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.soapdemo.photohunter.util.ItemTemplate;

import java.util.List;

public class ShadowAdapter<T> extends RecyclerView.Adapter<ShadowAdapter.ViewHolder> {
    private List<T> mDataSet;
    private ItemTemplate itemTemplate;

    public ShadowAdapter(List<T> dataSet , ItemTemplate itemTemplate) {
        mDataSet = dataSet;
        this.itemTemplate = itemTemplate;
        //int aaa = com.soapdemo.photohunter.BR.content;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                //.inflate(R.layout.text_row_item, parent, false);
        ViewDataBinding binding  = DataBindingUtil.inflate(inflater,itemTemplate.getTemplateId(),parent, false );
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
        assert binding != null;
        //binding.setContent(  (String)this.mDataSet.get(position));
        binding.setVariable( this.itemTemplate.getVariableId(), this.mDataSet.get(position) );
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
