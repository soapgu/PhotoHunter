package com.soapdemo.photohunter.viewmodels;


import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.soapdemo.photohunter.R;
import com.soapdemo.photohunter.models.Photo;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeViewModel extends ObservableViewModel {
    private static final String url = "https://api.unsplash.com/photos/random?client_id=ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM";
    private static OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    private String photoInfo = "Cow is Default Photo";
    private Bitmap bitmap;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        Bitmap cowImage = BitmapFactory.decodeResource(this.getApplication().getResources(), R.drawable.homephoto);
        this.setBitmap(cowImage);
    }

    @Bindable
    public String getPhotoInfo() {
        return photoInfo;
    }

    public void setPhotoInfo(String photeInfo) {
        this.photoInfo = photeInfo;
        this.notifyPropertyChanged(BR.photoInfo);
    }

    @Bindable
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.notifyPropertyChanged(BR.bitmap);
    }

    public void ChangePhoto()
    {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Logger.e("Get Photo Error:%s" , e.getMessage());
                setPhotoInfo("Error for Hunter Photo");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Logger.i("Response Code:%s",response.code());
                if( response.isSuccessful() ) {
                    Logger.i("Success fetch photo information , Thread:%s" , Thread.currentThread().getId());
                    try( ResponseBody responseBody = response.body() ) {
                        assert responseBody != null;
                        String jsonString = responseBody.string();
                        Photo photo = gson.fromJson(jsonString, Photo.class);
                        setPhotoInfo(photo.alt_description);
                        DownloadImage(photo);
                    }
                }
            }
        });
        setPhotoInfo("Requesting...");
    }

    private void DownloadImage( Photo photo )
    {
        Request imageRequest = new Request.Builder()
                .url(photo.urls.small)
                .get()
                .build();
        Call downloadCall = client.newCall(imageRequest);
        downloadCall.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Logger.e("download image Error:%s", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    Logger.i("Success response  photo image resource , Thread:%s" , Thread.currentThread().getId());
                    //FileOutputStream output = null;
                    try( ResponseBody downloadBody = response.body() ) {
                        assert downloadBody != null;
                        InputStream inputStream = downloadBody.byteStream();
                        BufferedInputStream input = new BufferedInputStream(inputStream);

                        File file = new File( getApplication().getExternalCacheDir(), String.format( "%s.jpg" ,photo.id ) );
                        try( FileOutputStream output = new FileOutputStream(file) )
                        {
                            int len;
                            byte[] data  = new byte[1024];
                            while ((len = input.read(data)) != -1) {
                                output.write(data, 0, len);
                            }
                            output.flush();
                            input.close();
                        }
                        Logger.i("----Success download photo image to cache---" );
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                        Logger.i("----Fetch photo image from cache---" );
                        setBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
