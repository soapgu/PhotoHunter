package com.soapdemo.photohunter.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.soapdemo.photohunter.adapters.ShadowAdapter;

import java.util.List;


public class BindingAdapters {
    @BindingAdapter("imageBitmap")
    public static void loadImage(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }

    @BindingAdapter("imageFromUrl")
    public  static void  loadGlide( ImageView imageView , String imageUrl ){
        if (!TextUtils.isEmpty(imageUrl) ) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"itemsSource","itemTemplate","variableName"},requireAll = false)
    public static <T> void setItems(RecyclerView recyclerView , List<T> itemsSource , @LayoutRes int itemTemplate, String variableName ){
        ShadowAdapter<T> adapter;

        @SuppressWarnings("unchecked")
        ShadowAdapter<T> oldAdapter = (ShadowAdapter<T>)recyclerView.getAdapter();
        if( oldAdapter == null ){
            ItemTemplate template = ItemTemplate.of( itemTemplate, variableName );
            adapter = new ShadowAdapter<>(template);
        }
        else {
            adapter = oldAdapter;
        }
        adapter.setItems(itemsSource);
        if (oldAdapter != adapter) {
            recyclerView.setAdapter(adapter);
        }
    }

}
