package com.soapdemo.photohunter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.soapdemo.photohunter.models.Photo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private String url = "https://api.unsplash.com/photos/random?client_id=ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM";
    private OkHttpClient client = new OkHttpClient();
    TextView textView;
    ImageView photoView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = this.findViewById((R.id.msg_view));
        photoView = this.findViewById(R.id.photo_view);
        this.findViewById(R.id.new_button).setOnClickListener( v ->{
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Call call = this.client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Logger.e("Get Photo Error:%s" , e.getMessage());
                    runOnUiThread(() -> textView.setText("Error for Hunter Photo"));
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Logger.i("Response Code:%s",response.code());
                    if( response.isSuccessful() ) {
                        Logger.i("Success fetch photo information , Thread:%s" , Thread.currentThread().getId());
                        Gson gson = new Gson();
                        ResponseBody responseBody = response.body();
                        if( responseBody != null ) {
                            Photo photo = gson.fromJson(responseBody.string(), Photo.class);
                            response.close();
                            runOnUiThread(() -> textView.setText(photo.alt_description));

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
                                        ResponseBody downloadBody = response.body();
                                        if (downloadBody != null) {
                                            InputStream inputStream = downloadBody.byteStream();
                                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                            response.close();
                                            runOnUiThread(() -> photoView.setImageBitmap(bitmap));
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            });
            textView.setText( "Requesting..." );
        } );
    }
}