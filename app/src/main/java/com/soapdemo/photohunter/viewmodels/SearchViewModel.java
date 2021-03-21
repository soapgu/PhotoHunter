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

    private ObservableList<String> photoItems;

    @Bindable
    public ObservableList<String> getPhotoItems() {
        return this.photoItems;
    }

    public void setStringItems(ObservableList<String> stringItems) {
        this.photoItems = stringItems;
        this.notifyPropertyChanged(BR.photoItems);
    }

    public void AddNewItem()
    {
        this.court++;
        this.getPhotoItems().add(String.valueOf(this.court));
    }

    public void Clear()
    {
        this.getPhotoItems().clear();
    }
}
