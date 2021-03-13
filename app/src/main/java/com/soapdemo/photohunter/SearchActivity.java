package com.soapdemo.photohunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.soapdemo.photohunter.adapters.CustomAdapter;
import com.soapdemo.photohunter.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.dataList.setLayoutManager( new LinearLayoutManager( this ) );
        CustomAdapter adapter = new CustomAdapter( new String[]{"A","B","C","E","F"} );
        binding.dataList.setAdapter( adapter );
    }
}