package com.soapdemo.photohunter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.soapdemo.photohunter.models.Photo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String url = "https://api.unsplash.com/photos/random?client_id=ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM";
    private OkHttpClient client = new OkHttpClient();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = this.findViewById((R.id.msg_view));
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
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Logger.i("Response OK");
                   //runOnUiThread( ()-> textView.setText( "OK" ) );
                    //Logger.i( "Body：%s", response.body().string() );
                    Gson gson = new Gson();
                    Photo photo = gson.fromJson( response.body().string(), Photo.class );
                    runOnUiThread( ()-> textView.setText( photo.alt_description ) );
                }
            });
            textView.setText( "Requesting..." );
        } );
    }
}