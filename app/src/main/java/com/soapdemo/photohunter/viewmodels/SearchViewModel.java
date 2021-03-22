package com.soapdemo.photohunter.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.soapdemo.photohunter.App;
import com.soapdemo.photohunter.models.Photo;
import com.soapdemo.photohunter.util.HttpClientWrapper;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Request;

public class SearchViewModel extends ObservableViewModel {

    private int court = 0;
    private static String url = "https://api.unsplash.com/photos?client_id=ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM";
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

    public void AddNewItem() {
        this.court++;
        Photo photo = new Photo();
        photo.alt_description = String.valueOf(this.court);
        this.getPhotoItems().add( photo );
    }

    public void Clear() {
        this.getPhotoItems().clear();
    }

    public  void SearchPhotos() {
        Type jsonType = new TypeToken<List<Photo>>() {}.getType();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        HttpClientWrapper.getInstance().<List<Photo>>ResponseGenericJson( request ,
                list -> {
                    App app = this.getApplication();
                    app.getMainThreadHandler().post( ()-> this.getPhotoItems().addAll(list));
                },
                e -> {
                    String errorMsg = e.getMessage();
                    Logger.e("Get Photo Error:%s" , errorMsg);
                },
                jsonType);
    }
}
