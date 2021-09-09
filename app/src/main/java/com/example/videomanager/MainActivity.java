package com.example.videomanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.videomanager.login.ApiClient;
import com.example.videomanager.login.LoginRequest;
import com.example.videomanager.login.LoginResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
TextView username;
private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.usernames);
        imageView = findViewById(R.id.imageIcon);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            String passedUsername = intent.getStringExtra("data");
            username.setText(passedUsername);

            String image = intent.getStringExtra("data1");

//            Glide.with(this)
//                    .load(image)
//                    .into(imageView);

            Glide.with(this).load(image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        }

    }
}
