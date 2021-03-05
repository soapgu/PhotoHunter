package com.soapdemo.photohunter;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class App extends Application {
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public Handler getMainThreadHandler()
    {
        return  mainThreadHandler;
    }

    @Override
    public void onCreate() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag("SoapAPP")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.i("-------APP Create-------");
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.i("-------APP Terminate-------");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.i("-------APP Config Changed--------");
    }
}
