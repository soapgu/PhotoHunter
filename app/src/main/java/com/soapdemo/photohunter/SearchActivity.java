package com.soapdemo.photohunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.soapdemo.photohunter.adapters.CustomAdapter;
import com.soapdemo.photohunter.databinding.ActivitySearchBinding;
import com.soapdemo.photohunter.viewmodels.HomeViewModel;
import com.soapdemo.photohunter.viewmodels.SearchViewModel;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchViewModel viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(SearchViewModel.class);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.dataList.setLayoutManager( new LinearLayoutManager( this ) );
        binding.setDatacontext( viewModel );
    }
}