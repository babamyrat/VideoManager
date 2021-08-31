package com.example.videomanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videomanager.login.ApiClient;
import com.example.videomanager.login.LoginRequest;
import com.example.videomanager.login.LoginResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputEditText username, password;
    MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(MainActivity.this, "Парол или пароль пустой", Toast.LENGTH_SHORT).show();
                } else {
                    //proceed to login
                }
            }
        });


    }

    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginResponse> loginRequestCall = ApiClient.getUserService().userLogin(loginRequest);
        loginRequestCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


    }
}