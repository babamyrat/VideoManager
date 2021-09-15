package com.example.videomanager.ui;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.videomanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
private TextView username, first_name;
private ImageView imageView;
private CardView cardView, cardClick;
private MaterialButton button;
private ConstraintLayout exConstrain;
private SharedPreferences pref;
private final String save_key = "KEY";
private LinearLayout exitLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.usernames);
        first_name = findViewById(R.id.txtName);
        imageView = findViewById(R.id.imageIcon);
        cardClick = findViewById(R.id.cardClick);
        button = findViewById(R.id.btnClick);

        cardView = findViewById(R.id.cardView);
        exConstrain = findViewById(R.id.exConstrain);
        exitLayout = findViewById(R.id.exitLayout);




        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });




        exitLayout.setOnClickListener(new View.OnClickListener() {
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

            String name = intent.getStringExtra("data2");
            first_name.setText(name);

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
