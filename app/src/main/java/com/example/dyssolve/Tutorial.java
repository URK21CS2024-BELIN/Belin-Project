package com.example.dyssolve;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

import java.util.Locale;

public class Tutorial extends AppCompatActivity {
    private TextView titleTextView;
    private TextView questionTextView;
    private TextView morseTextView;
    private Button dotButton;
    private Button dashButton;
    TextToSpeech t1;

    private int currentQuestion = 0; // variable to keep track of current question

    private String[] numberAnswers = {"-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...",
            "---..", "----."}; // array of correct Morse code answers for numbers 0-9

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_tutorial);

        titleTextView = findViewById(R.id.title_text_view);
        questionTextView = findViewById(R.id.question_text_view);
        morseTextView = findViewById(R.id.morse_text_view);
        dotButton = findViewById(R.id.dot_button);
        dashButton = findViewById(R.id.dash_button);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                    t1.setLanguage(Locale.ENGLISH);
            }
        });

        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String morse = morseTextView.getText().toString();
                morse += ".";
                morseTextView.setText(morse);
                checkAnswer();
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        });

        dashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String morse = morseTextView.getText().toString();
                morse += "-";
                morseTextView.setText(morse);
                checkAnswer();
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 100};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
                }
            }
        });
        // Set initial question
        titleTextView.setText("MORSE GAME");
        questionTextView.setText("Enter the Morse code for number 0");
        String text2 = questionTextView.getText().toString();
        t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
        morseTextView.setText("");


    }
    private void setNextQuestion() {
        if (currentQuestion == 9) { // if last question, display congratulations message
            titleTextView.setText("Congratulations!");
            questionTextView.setText("You have completed the game.");
            morseTextView.setVisibility(View.GONE);
            dotButton.setVisibility(View.GONE);
            dashButton.setVisibility(View.GONE);
        } else { // otherwise, move to next number
            currentQuestion++;
            //titleTextView.setText("Question " + currentQuestion);
            questionTextView.setText("Enter the Morse code for number " + (currentQuestion));
            morseTextView.setText("");
            String text2 = questionTextView.getText().toString();
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void checkAnswer() {
        String morse = morseTextView.getText().toString();
        boolean isCorrect = false;

        if (morse.equals(numberAnswers[currentQuestion])) {
            isCorrect = true;
            if (isCorrect) {
                // Move to next question
                setNextQuestion();
            }}

    }
}