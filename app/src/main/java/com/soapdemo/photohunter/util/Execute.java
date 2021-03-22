package com.soapdemo.photohunter.util;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

/***
 * Enables easy marshalling of code to the UI thread.
 */
public class Execute {
    private static volatile Execute instance;
    private final Handler mainThreadHandler;

    private Execute()
    {
        this.mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    }

    public static Execute getInstance()
    {
        if( instance == null ) {
            synchronized ( Execute.class ) {
                if( instance == null ){
                    instance = new Execute();
                }
            }
        }
        return instance;
    }

    /**
     * Post Delegate to UI Thread
     * @param r 相关代理
     */
    public void BeginOnUIThread(Runnable r)
    {
        this.mainThreadHandler.post(r);
    }


}
