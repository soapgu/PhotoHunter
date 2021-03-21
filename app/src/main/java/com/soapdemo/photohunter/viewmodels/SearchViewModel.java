package com.soapdemo.photohunter.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.library.baseAdapters.BR;

import com.soapdemo.photohunter.models.Photo;

public class SearchViewModel extends ObservableViewModel {

    private int court = 0;
    public SearchViewModel(@NonNull Application application) {
        super(application);
        ObservableList<Photo> ol = new ObservableArrayList<>();
        this.setStringItems( ol );
    }

    private ObservableList<Photo> photoItems;

    @Bindable
    public ObservableList<Photo> getPhotoItems() {
        return this.photoItems;
    }

    public void setStringItems(ObservableList<Photo> stringItems) {
        this.photoItems = stringItems;
        this.notifyPropertyChanged(BR.photoItems);
    }

    public void AddNewItem()
    {
        this.court++;
        Photo photo = new Photo();
        photo.alt_description = String.valueOf(this.court);
        this.getPhotoItems().add( photo );
    }

    public void Clear()
    {
        this.getPhotoItems().clear();
    }
}
