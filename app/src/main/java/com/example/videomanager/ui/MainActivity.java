package com.example.videomanager.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.videomanager.R;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
private TextView username;
private ImageView imageView;
private CardView cardView, cardClick;
private MaterialButton button;
private ConstraintLayout exConstrain;
private SharedPreferences pref;
private final String save_key = "KEY";
private TextView txtClickClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.usernames);
        imageView = findViewById(R.id.imageIcon);
        cardClick = findViewById(R.id.cardClick);
        button = findViewById(R.id.btnClick);

        cardView = findViewById(R.id.cardView);
        exConstrain = findViewById(R.id.exConstrain);
        txtClickClear = findViewById(R.id.txtExit);



        txtClickClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alert Dialog yes or no
                 new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Выйти из аккаунта!")
                        .setMessage("Вы действительно хотите выйти из аккаунта?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //View Display
                                Toast.makeText(MainActivity.this, "Выйти из аккаунта!", Toast.LENGTH_SHORT).show();

                                //SET SharedPreferences
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString(save_key,"");
                                editor.apply();

                                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent1);

                            }
                        })
                        .setNegativeButton("Нет", null)
                        .show();

            }
        });

        //group
        init();

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            String passedUsername = intent.getStringExtra("data");
            username.setText(passedUsername);

            String image = intent.getStringExtra("data1");

            Glide.with(this).load(image)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);

            String token = intent.getStringExtra("token");

            //SET SharedPreferences
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(save_key,token);
            editor.apply();

        }


        // click button down and up
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exConstrain.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    exConstrain.setVisibility(View.VISIBLE);
                    button.setBackgroundResource(R.drawable.ic_down);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    exConstrain.setVisibility(View.GONE);
                    button.setBackgroundResource(R.drawable.ic_up);
                }
            }
        });


        // click call
        cardClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, CallViewActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void init(){

        pref = getSharedPreferences("Test", MODE_PRIVATE);

    }

}
