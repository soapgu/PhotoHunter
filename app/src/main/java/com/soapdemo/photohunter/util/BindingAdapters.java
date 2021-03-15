package com.soapdemo.photohunter.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.soapdemo.photohunter.adapters.ShadowAdapter;

import java.util.List;

public class BindingAdapters {
    @BindingAdapter("imageBitmap")
    public static void loadImage(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }

    @BindingAdapter({"itemsSource","itemTemplate"})
    public static <T> void setItems(RecyclerView recyclerView , List<T> itemsSource , ItemTemplate itemTemplate ){
        //ItemTemplate itemTemplate = ItemTemplate.of( BR.content, @layout/text_row_item)
        ShadowAdapter<T> adapter = new ShadowAdapter<>( itemsSource,itemTemplate);
        recyclerView.setAdapter( adapter );
    }

}
