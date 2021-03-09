package com.soapdemo.photohunter.util;

import androidx.core.util.Consumer;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

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
     * @param request 请求对象
     * @param onSuccess 成功返回json回调
     * @param onError 失败回调
     * @param classOfT json类型
     * @param <T> json类型泛型
     */
    public <T> void ResponseJson(Request request , Consumer<T> onSuccess , Consumer<Exception> onError, Class<T> classOfT)
    {
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.accept( e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try (ResponseBody body = response.body())
                {
                    if( response.isSuccessful() ) {
                        try {
                            assert body != null;
                            String json = body.string();
                            Gson gson = new Gson();
                            T jsonObj = gson.fromJson(json,classOfT );
                            onSuccess.accept(jsonObj);
                        } catch (Exception e) {
                            onError.accept(e);
                        }
                    }
                    else
                    {
                        onError.accept( new Exception( String.format( "error state code: %s", response.code() ) ) );
                    }
                }
                catch ( Exception e )
                {
                    onError.accept(e);
                }
            }
        });
    }

    /**
     * 处理返回Stream内容
     * @param request 请求对象
     * @param onSuccess 成功返回body的stream回调
     * @param onError 失败回调
     */
    public void ResponseStream(Request request ,  Consumer<InputStream> onSuccess , Consumer<Exception> onError)
    {
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.accept( e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response){
                try (ResponseBody body = response.body())
                {
                    if( response.isSuccessful() ) {
                        InputStream stream = Objects.requireNonNull(response.body()).byteStream();
                        onSuccess.accept(stream);
                    }
                    else
                    {
                        onError.accept( new Exception( String.format( "error state code: %s", response.code() ) ) );
                    }
                }
                catch ( Exception e )
                {
                    onError.accept(e);
                }
            }
        });
    }
}

