package com.example.videomanager.ui;

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
import com.example.videomanager.login.ApiClient;
import com.example.videomanager.login.LoginRequest;
import com.example.videomanager.login.LoginResponse;
import com.example.videomanager.model.LoginCall;
import com.example.videomanager.model.LoginToken;
import com.example.videomanager.push.FirebaseMessageReceiver;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText username, password;
    private MaterialButton btnLogin;
    private static String token;
    private static LoginCall tokenFirebase;
    private ProgressBar progressBar;
    private LoginResponse loginResponse;


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
      Call<LoginResponse> userInfo = ApiClient.getUserService().userMe("Token " + token );
      userInfo.enqueue(new Callback<LoginResponse>() {
          @Override
          public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
              if (response.isSuccessful()) {
                  //progressBar.setVisibility(View.VISIBLE);
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          LoginResponse loginResponse = response.body();
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
          public void onFailure(Call<LoginResponse> call, Throwable t) {

          }
      });

    }

private final String firebaseToken = "sfasdfsdfaeefasdf";

    private void sendTokenToServer(){
        LoginCall loginCall = new LoginCall();
        loginCall.setRegistrationToken("TokenFire");
        loginCall.setManagerId(loginResponse.getManagerId());
        loginCall.setType("Android");
        Call<LoginCall> userCall = ApiClient.getUserService().userCall("Token " + token);
        userCall.enqueue(new Callback<LoginCall>() {
            @Override
            public void onResponse(Call<LoginCall> call, Response<LoginCall> response) {

                if (response.isSuccessful()) {

                   LoginCall loginCall = response.body();



                }

            }

            @Override
            public void onFailure(Call<LoginCall> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void login(){

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginToken> loginRequestCall = ApiClient.getUserService().userLogin(loginRequest);
        loginRequestCall.enqueue(new Callback<LoginToken>() {
            @Override
            public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {
                if (response.isSuccessful()){
                    //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    token = response.body().getToken();
                   // getUserInfo(token);


                } else {
                    progressBar.setVisibility(View.GONE);
                    username.setTextColor(Color.RED);
                    password.setTextColor(Color.RED);

                }

            }


            @Override
            public void onFailure(Call<LoginToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }
}