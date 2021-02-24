package com.soapdemo.photohunter;

import androidx.annotation.NonNull;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private static OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    TextView textView;
    ImageView photoView;
    String currentPhotoJson;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = this.findViewById((R.id.msg_view));
        photoView = this.findViewById(R.id.photo_view);
        if( savedInstanceState != null ) {
            String savedJson = savedInstanceState.getString("Json");
            if( savedJson != null ) {
                this.currentPhotoJson = savedJson;
                Photo photo = gson.fromJson(savedJson, Photo.class);
                File file = new File(getExternalCacheDir(), String.format( "%s.jpg" ,photo.id ) );
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                runOnUiThread(() -> photoView.setImageBitmap(bitmap));
            }
        }
        this.findViewById(R.id.new_button).setOnClickListener( v ->{
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Call call = client.newCall(request);

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
                        try( ResponseBody responseBody = response.body() ) {
                            String jsonString = responseBody.string();
                            Photo photo = gson.fromJson(jsonString, Photo.class);
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
                                        FileOutputStream fos = null;
                                        try( ResponseBody downloadBody = response.body() ) {
                                            InputStream inputStream = downloadBody.byteStream();
                                            byte[] buf = new byte[2048];
                                            File file = new File(getExternalCacheDir(), String.format( "%s.jpg" ,photo.id ) );
                                            fos = new FileOutputStream(file);
                                            int len;
                                            while ((len = inputStream.read(buf)) != -1) {
                                                fos.write(buf, 0, len);
                                            }
                                            fos.flush();
                                            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                                            runOnUiThread(() -> photoView.setImageBitmap(bitmap));
                                            currentPhotoJson = jsonString;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (fos != null) {
                                                try {
                                                    fos.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if( this.currentPhotoJson != null ) {
            //Logger.i("Save Photo Json: %s", this.currentPhotoJson);
            outState.putString("Json", this.currentPhotoJson);
        }
    }
}