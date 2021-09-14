package com.example.videomanager.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.videomanager.R;
import com.example.videomanager.login.ApiClient;
import com.example.videomanager.login.LoginRequest;
import com.example.videomanager.login.LoginResponse;
import com.example.videomanager.model.LoginToken;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText username, password;
    private MaterialButton btnLogin;
    private static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Парол или пароль пустой", Toast.LENGTH_SHORT).show();
                } else {
                    //proceed to login
                    login();

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
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          LoginResponse loginResponse = response.body();
                          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                          intent.putExtra("data", loginResponse.getEmail());
                          intent.putExtra("data1", loginResponse.getAvatar());
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
                    getUserInfo(token);

                }

            }


            @Override
            public void onFailure(Call<LoginToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }
}