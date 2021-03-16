package com.soapdemo.photohunter.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.library.baseAdapters.BR;

public class SearchViewModel extends ObservableViewModel {

    private int court = 0;
    public SearchViewModel(@NonNull Application application) {
        super(application);
        ObservableList<String> ol = new ObservableArrayList<>();
        this.setStringItems( ol );
    }

    private ObservableList<String> stringItems;

    @Bindable
    public ObservableList<String> getStringItems() {
        return this.stringItems;
    }

    public void setStringItems(ObservableList<String> stringItems) {
        this.stringItems = stringItems;
        this.notifyPropertyChanged(BR.stringItems);
    }

    public void AddNewItem()
    {
        this.court++;
        this.getStringItems().add(String.valueOf(this.court));
    }

    public void Clear()
    {
        this.getStringItems().clear();
    }
}
