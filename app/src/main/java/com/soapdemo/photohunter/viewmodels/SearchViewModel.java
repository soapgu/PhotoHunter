package com.soapdemo.photohunter.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchViewModel extends ObservableViewModel {
    public SearchViewModel(@NonNull Application application) {
        super(application);
        this.setStringItems(new ArrayList<>(Arrays.asList("AAA", "BBB", "CCC", "DDD", "EEE", "FFF","G","H","I","J","K")));
    }

    private List<String> stringItems;

    @Bindable
    public List<String> getStringItems() {
        return this.stringItems;
    }

    public void setStringItems(List<String> stringItems) {
        this.stringItems = stringItems;
        this.notifyPropertyChanged(BR.stringItems);
    }
}
