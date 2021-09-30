package com.example.videomanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.videomanager.R;

import com.example.videomanager.data.api.ApiClient;
import com.example.videomanager.data.model.CallModel;
import com.example.videomanager.data.model.InfoModel;
import com.example.videomanager.data.model.SingModel;
import com.example.videomanager.data.model.TokenModel;

import com.example.videomanager.push.FirebaseMessageReceiver;
import com.example.videomanager.ui.activity.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText username, password;
    private MaterialButton btnLogin;
    private static String token;
    private static CallModel tokenFirebase;
    private ProgressBar progressBar;
    private InfoModel loginResponse;
    private FirebaseMessageReceiver messageReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setTextColor(Color.BLACK);
                password.setTextColor(Color.BLACK);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setTextColor(Color.BLACK);
                password.setTextColor(Color.BLACK);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(LoginActivity.this, "пуст", Toast.LENGTH_SHORT).show();
                } else {
                    //proceed to login
                    login();

                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    public void getUserInfo(String token) {
      Call<InfoModel> userInfo = ApiClient.getUserService().userMe("Token " + token );
      userInfo.enqueue(new Callback<InfoModel>() {
          @Override
          public void onResponse(Call<InfoModel> call, Response<InfoModel> response) {
              if (response.isSuccessful()) {
                  //progressBar.setVisibility(View.VISIBLE);
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          InfoModel loginResponse = response.body();
                          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                          intent.putExtra("data", loginResponse.getEmail());
                          intent.putExtra("data1", loginResponse.getAvatar());
                          intent.putExtra("data2", loginResponse.getFirstName());
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


    private void  sendTokenToServer(){
        CallModel loginCall = new CallModel();
        loginCall.setRegistrationToken(messageReceiver.sendTokenToServer(""));
        loginCall.setManagerId(loginResponse.getManagerId());
        loginCall.setType("Android");
        Call<CallModel> userCall = ApiClient.getUserService().userCall("Token " + token);
        userCall.enqueue(new Callback<CallModel>() {
            @Override
            public void onResponse(Call<CallModel> call, Response<CallModel> response) {

                if (response.isSuccessful()) {

                   CallModel loginCall = response.body();



                }
            }

            @Override
            public void onFailure(Call<CallModel> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void login(){

        SingModel loginRequest = new SingModel();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<TokenModel> loginRequestCall = ApiClient.getUserService().userLogin(loginRequest);
        loginRequestCall.enqueue(new Callback<TokenModel>() {

            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()){
                    //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    token = response.body().getToken();
                    getUserInfo(token);
                   // sendTokenToServer();


                } else {
                    progressBar.setVisibility(View.GONE);
                    username.setTextColor(Color.RED);
                    password.setTextColor(Color.RED);

                }

            }


            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }
}