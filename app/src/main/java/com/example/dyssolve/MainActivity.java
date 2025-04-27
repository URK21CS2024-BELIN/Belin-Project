package com.example.dyssolve;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ImageButton morseTypeButt, ttm_button, Sph_to_mor_butt, pdf_button, LogOutButton, Article_button, Tutorial_btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //following three lines to hide the top bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        LogOutButton = findViewById(R.id.imageButton8);
        ttm_button = findViewById(R.id.imageButton2);
        Sph_to_mor_butt = findViewById(R.id.imageButton3);
        pdf_button = findViewById(R.id.imageButton4);
        Article_button = findViewById(R.id.imageButton6);
        Tutorial_btn = findViewById(R.id.imageButton5);


        LogOutButton.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
        morseTypeButt = findViewById(R.id.imageButton);
        morseTypeButt.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, morse_typer.class));
        });

        ttm_button.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, TextToMorse.class));
        });

        Sph_to_mor_butt.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, SpeechToMorse.class));
        });

        pdf_button.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, PdfToMorse.class));
        });
        Article_button.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, MainArticle.class));
        });
        Tutorial_btn.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, Tutorial.class));
        });



    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}