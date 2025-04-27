package com.example.dyssolve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Locale;

public class MainArticle extends AppCompatActivity {
    Button A1, A2;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main_article);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                    t1.setLanguage(Locale.ENGLISH);
            }
        });

        A1 = findViewById(R.id.button3);
        A2 = findViewById(R.id.button4);

        A1.setOnClickListener(view ->{
            startActivity(new Intent(MainArticle.this, Article1.class));
        });
        A2.setOnClickListener(view ->{
            startActivity(new Intent(MainArticle.this, Article2.class));
        });

    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action, keycode;
        action = event.getAction();
        keycode = event.getKeyCode();

        if (keycode == KeyEvent.KEYCODE_VOLUME_UP){
            if(KeyEvent.ACTION_UP == action){
                String intro_text = ("This article reader is used to read samples of text and morse for learning purposes.").toString();
                t1.speak(intro_text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        return super.dispatchKeyEvent(event);
    }
}