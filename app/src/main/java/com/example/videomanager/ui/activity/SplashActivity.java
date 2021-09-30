package com.example.videomanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.videomanager.R;
import com.example.videomanager.data.api.ApiClient;
import com.example.videomanager.data.model.InfoModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=1000;

    private SharedPreferences pref;
    private final String save_key = "KEY";
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

          //full screen View
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //GET
        pref = getSharedPreferences("Test", MODE_PRIVATE);
        token = pref.getString(save_key,"");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (token.isEmpty()){
                    Intent i=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                } else {

                    getUserInfo(token);

                }

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }


    public void getUserInfo(String token) {
        Call<InfoModel> userInfo = ApiClient.getUserService().userMe("Token " + token );
        userInfo.enqueue(new Callback<InfoModel>() {
            @Override
            public void onResponse(Call<InfoModel> call, Response<InfoModel> response) {
                if (response.isSuccessful()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            InfoModel infoModel = response.body();
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.putExtra("data", infoModel.getEmail());
                            intent.putExtra("data1", infoModel.getAvatar());
                            intent.putExtra("data2", infoModel.getFirstName());
                            intent.putExtra("token", token);
                            startActivity(intent);

                        }
                    }, 700);
                }
            }

            @Override
            public void onFailure(Call<InfoModel> call, Throwable t) {

            }
        });
    }
}