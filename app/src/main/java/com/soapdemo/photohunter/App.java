package com.soapdemo.photohunter;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class App extends Application {
    final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler =
            Thread.getDefaultUncaughtExceptionHandler();

    @Override
    public void onCreate() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag("SoapAPP")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.i("-------APP Create-------");
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) -> {
            Logger.e( paramThrowable, "----------UncaughtException throw---------" );
            if( defaultUncaughtExceptionHandler != null ){
                defaultUncaughtExceptionHandler.uncaughtException( paramThread,paramThrowable );
            }
        });
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
