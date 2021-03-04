package com.soapdemo.photohunter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.soapdemo.photohunter.databinding.ActivityMainBinding;
import com.soapdemo.photohunter.viewmodels.HomeViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeViewModel viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(HomeViewModel.class);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewmodel( viewModel );
    }


}