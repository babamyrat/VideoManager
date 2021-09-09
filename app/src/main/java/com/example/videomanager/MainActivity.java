package com.example.videomanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
private TextView username;
private ImageView imageView;
private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.usernames);
        imageView = findViewById(R.id.imageIcon);
        cardView = findViewById(R.id.cardClick);


        Intent intent = getIntent();
        if (intent.getExtras() != null){
            String passedUsername = intent.getStringExtra("data");
            username.setText(passedUsername);

            String image = intent.getStringExtra("data1");

            Glide.with(this).load(image)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        }


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, CallViewActivity.class);
                startActivity(intent1);
            }
        });
    }

}
