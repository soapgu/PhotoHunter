package com.soapdemo.photohunter.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.soapdemo.photohunter.adapters.CustomAdapter;

import java.util.List;

public class BindingAdapters {
    @BindingAdapter("imageBitmap")
    public static void loadImage(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }

    @BindingAdapter("itemsSource")
    public static void setItems(RecyclerView recyclerView , List<String> items ){
        CustomAdapter adapter = new CustomAdapter( items.toArray( new String[0]) );
        recyclerView.setAdapter( adapter );
    }

}
