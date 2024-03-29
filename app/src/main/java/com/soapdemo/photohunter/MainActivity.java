package com.soapdemo.photohunter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.orhanobut.logger.Logger;
import com.soapdemo.photohunter.databinding.ActivityMainBinding;
import com.soapdemo.photohunter.viewmodels.HomeViewModel;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i( "---Create MainActivity----" );
        super.onCreate(savedInstanceState);
        HomeViewModel viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(HomeViewModel.class);
        ActivityMainBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setDatacontext( viewModel );
        binding.searchButton.setOnClickListener( v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i( "---Stop MainActivity----" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i( "---Destroy MainActivity----" );
    }
}