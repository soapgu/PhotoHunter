package com.soapdemo.photohunter.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.soapdemo.photohunter.models.PageResults;
import com.soapdemo.photohunter.models.Photo;
import com.soapdemo.photohunter.util.Execute;
import com.soapdemo.photohunter.util.HttpClientWrapper;
import com.soapdemo.photohunter.util.MessageHelper;

import java.lang.reflect.Type;

import okhttp3.Request;

public class SearchViewModel extends ObservableViewModel {

    private int court = 0;
    private static String url = "https://api.unsplash.com/search/photos?client_id=ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM&query=%s}";
    private String searchKey = "cow";

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

    @Bindable
    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        if(!searchKey.equals(this.searchKey)) {
            this.searchKey = searchKey;
            notifyPropertyChanged(BR.searchKey);
        }
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
        Type jsonType = new TypeToken<PageResults<Photo>>() {}.getType();
        Request request = new Request.Builder()
                .url( String.format( url , this.searchKey ) )
                .get()
                .build();
        HttpClientWrapper.getInstance().<PageResults<Photo>>ResponseGenericJson( request ,
                result -> Execute.getInstance().BeginOnUIThread( ()-> this.getPhotoItems().addAll(result.results)),
                e -> {
                    String errorMsg = String.format("Search Photo List Error:%s" , e.getMessage() );
                    Logger.e( e, "Search Photo List Error" );
                    this.ShowToastInfo( errorMsg);
                },
                jsonType);
        this.ShowToastInfo( "get photo list...");
        if( !this.getPhotoItems().isEmpty() ) {
            this.Clear();
        }
    }

    private void ShowToastInfo( String message ) {
        MessageHelper.ShowToast( this.getApplication(),message);
    }
}
