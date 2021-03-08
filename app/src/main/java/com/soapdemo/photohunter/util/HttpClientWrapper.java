package com.soapdemo.photohunter.util;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpClientWrapper {
    private static OkHttpClient client = new OkHttpClient();

    /**
     * 处理返回json内容Response
     * @param request
     * @param onSuccess
     * @param onError
     * @param classOfT
     * @param <T>
     */
    public <T> void ResponseJson(Request request , HttpJsonCallback<T> onSuccess , HttpErrorCallback onError, Class<T> classOfT)
    {
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.OnError( e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try (ResponseBody body = response.body())
                {
                    if( response.isSuccessful() ) {
                        try {
                            String json = body.string();
                            Gson gson = new Gson();
                            T jsonObj = gson.fromJson(json,classOfT );
                            onSuccess.onResponse(jsonObj);
                        } catch (Exception e) {
                            onError.OnError(e);
                        }
                    }
                    else
                    {
                        onError.OnError( new Exception( String.format( "error state code: %s", response.code() ) ) );
                    }
                }
                catch ( Exception e )
                {
                    onError.OnError(e);
                }
            }
        });
    }

    /**
     * 处理返回Stream内容
     * @param request
     * @param onSuccess
     * @param onError
     */
    public void ResponseStream(Request request ,  HttpInputStreamCallback onSuccess , HttpErrorCallback onError)
    {
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.OnError( e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response){
                try (ResponseBody body = response.body())
                {
                    if( response.isSuccessful() ) {
                        InputStream stream = response.body().byteStream();
                        onSuccess.onResponse(stream);
                    }
                    else
                    {
                        onError.OnError( new Exception( String.format( "error state code: %s", response.code() ) ) );
                    }
                }
                catch ( Exception e )
                {
                    onError.OnError(e);
                }
            }
        });
    }
}

