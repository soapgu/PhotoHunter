package com.soapdemo.photohunter.viewmodels;


import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.soapdemo.photohunter.App;
import com.soapdemo.photohunter.MainActivity;
import com.soapdemo.photohunter.R;
import com.soapdemo.photohunter.models.Photo;
import com.soapdemo.photohunter.util.HttpClientWrapper;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeViewModel extends ObservableViewModel {
    private static final String url = "https://api.unsplash.com/photos/random?client_id=ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM";
    private HttpClientWrapper httpClientWrapper;
    private Timer timer;

    private String photoInfo = "Cow is Default Photo";
    private Bitmap bitmap;
    private int countSec = 0;
    private boolean isCount = false;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        httpClientWrapper = new HttpClientWrapper();
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

    @Bindable
    public int getCountSec() {
        return countSec;
    }

    @Bindable
    public boolean getIsCount() {
        return isCount;
    }


    public void StartOrStop()
    {
        if( this.isCount )
            this.Stop();
        else
            this.Start();
    }

    private void Stop()
    {
        if( this.isCount ) {
            this.timer.cancel();
            this.timer.purge();
            this.isCount = false;
            this.notifyPropertyChanged( BR.isCount );
        }
    }

    private  void Start()
    {
        if(!this.isCount) {
            timer = new Timer();
            timer.schedule( new HomeViewModel.CountTask(), 0 ,1000 );
            this.isCount = true;
            this.notifyPropertyChanged( BR.isCount );
        }
    }

    public class CountTask extends TimerTask {
        @Override
        public void run() {
            countSec++;
            if( countSec == 10 )
                ChangePhoto();
            else if( countSec > 10 )
                countSec = 1;
            notifyPropertyChanged(BR.countSec);
        }
    }


    public void ChangePhoto()
    {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        httpClientWrapper.ResponseJson( request ,
                photo -> {
                    setPhotoInfo(photo.alt_description);
                    DownloadImage(photo);
                },
                e -> {
                    String errorMsg = e.getMessage();
                    Logger.e("Get Photo Error:%s" , errorMsg);
                    ShowToastInfo(String.format( "Error for Hunter Photo:%s", errorMsg));
                },
                Photo.class);
        ShowToastInfo("Requesting photo info...");
    }

    private void DownloadImage( Photo photo )
    {
        Request imageRequest = new Request.Builder()
                .url(photo.urls.small)
                .get()
                .build();
        httpClientWrapper.ResponseStream( imageRequest,
                stream -> {
                    BufferedInputStream input = new BufferedInputStream(stream);

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
                    } catch (IOException e) {
                        String errorMsg = e.getMessage();
                        Logger.e("Download Photo Error:%s" , errorMsg);
                        ShowToastInfo(String.format( "Error for download Photo:%s", errorMsg));
                    }
                    Logger.i("----Success download photo image to cache---" );
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    Logger.i("----Fetch photo image from cache---" );
                    setBitmap(bitmap);
                },
                e -> {
                    String errorMsg = e.getMessage();
                    Logger.e("Download Photo Error:%s" , errorMsg);
                    ShowToastInfo(String.format( "Error for download Photo:%s", errorMsg));
                });
    }

    private void ShowToastInfo( String message )
    {
        App app = this.getApplication();
        app.getMainThreadHandler().post(() -> {
            Toast toast = Toast.makeText(app, message , Toast.LENGTH_SHORT);
            toast.show();
        });
    }
}
