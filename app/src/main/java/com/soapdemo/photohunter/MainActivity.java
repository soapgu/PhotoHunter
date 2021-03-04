package com.soapdemo.photohunter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.soapdemo.photohunter.databinding.ActivityMainBinding;
import com.soapdemo.photohunter.viewmodels.HomeViewModel;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeViewModel viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(HomeViewModel.class);
        ActivityMainBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewmodel( viewModel );
        binding.searchButton.setOnClickListener( v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } );
    }


}