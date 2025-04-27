package com.example.dyssolve;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import java.util.Locale;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

public class TextToMorse extends AppCompatActivity {
    private static final Map<Character, String> MORSE_MAP = new HashMap<>();

    static {
        MORSE_MAP.put('A', ".-");
        MORSE_MAP.put('B', "-...");
        MORSE_MAP.put('C', "-.-.");
        MORSE_MAP.put('D', "-..");
        MORSE_MAP.put('E', ".");
        MORSE_MAP.put('F', "..-.");
        MORSE_MAP.put('G', "--.");
        MORSE_MAP.put('H', "....");
        MORSE_MAP.put('I', "..");
        MORSE_MAP.put('J', ".---");
        MORSE_MAP.put('K', "-.-");
        MORSE_MAP.put('L', ".-..");
        MORSE_MAP.put('M', "--");
        MORSE_MAP.put('N', "-.");
        MORSE_MAP.put('O', "---");
        MORSE_MAP.put('P', ".--.");
        MORSE_MAP.put('Q', "--.-");
        MORSE_MAP.put('R', ".-.");
        MORSE_MAP.put('S', "...");
        MORSE_MAP.put('T', "-");
        MORSE_MAP.put('U', "..-");
        MORSE_MAP.put('V', "...-");
        MORSE_MAP.put('W', ".--");
        MORSE_MAP.put('X', "-..-");
        MORSE_MAP.put('Y', "-.--");
        MORSE_MAP.put('Z', "--..");
        MORSE_MAP.put('0', "-----");
        MORSE_MAP.put('1', ".----");
        MORSE_MAP.put('2', "..---");
        MORSE_MAP.put('3', "...--");
        MORSE_MAP.put('4', "....-");
        MORSE_MAP.put('5', ".....");
        MORSE_MAP.put('6', "-....");
        MORSE_MAP.put('7', "--...");
        MORSE_MAP.put('8', "---..");
        MORSE_MAP.put('9', "----.");
        MORSE_MAP.put('.', ".-.-.-");
        MORSE_MAP.put(',', "--..--");
        MORSE_MAP.put('?', "..--..");
        MORSE_MAP.put('!', "-.-.--");
        MORSE_MAP.put('\'', ".----.");
        MORSE_MAP.put('\"', ".-..-.");
        MORSE_MAP.put('(', "-.--.");
        MORSE_MAP.put(')', "-.--.-");
        MORSE_MAP.put('&', ".-...");
        MORSE_MAP.put(':', "---...");
        MORSE_MAP.put(';', "-.-.-.");
        MORSE_MAP.put('/', "-..-.");
        MORSE_MAP.put('_', "..--.-");
        MORSE_MAP.put('+', ".-.-.");
        MORSE_MAP.put('-', "-....-");
        MORSE_MAP.put('=', "-...-");
        MORSE_MAP.put('@', ".--.-.");
        MORSE_MAP.put(' ', "/");
    }
    private static final int MY_PERMISSIONS_REQUEST_VIBRATE = 123;
    private TextView mMorseCodeTextView;
    private String input;

    private String translateToMorseCode(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            String morseCode = MORSE_MAP.get(c);
            if (morseCode != null) {
                morseCodeBuilder.append(morseCode).append(" ");
            }
        }
        return morseCodeBuilder.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String morseCode = translateToMorseCode(input);
        vibrateMorseCode(morseCode);

    }

    private void vibrateMorseCode(final String morseCode) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final long dotDuration = 200; // in milliseconds
        final long dashDuration = 3 * dotDuration;
        final long spaceDuration = 1000;
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < morseCode.length(); i++) {
                    final char c = morseCode.charAt(i);
                    if (c == '.') {
                        vibrator.vibrate(dotDuration);
                    } else if (c == '-') {
                        vibrator.vibrate(dashDuration);
                    } else if (c == '/') {
                        try {
                            Thread.sleep(spaceDuration);
                        } catch (InterruptedException e) {
                            Log.e("MainActivity", "Error: " + e.getMessage());
                        }
                    }
                    try {
                        Thread.sleep(dotDuration * 4);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }

    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //following three lines to hide the top bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_text_to_morse);

        EditText inputEditText = findViewById(R.id.input_edit_text);
        mMorseCodeTextView = findViewById(R.id.morse_code_text_view);
        Button translateButton = findViewById(R.id.translate_button);
        Button generatespeech = findViewById(R.id.generatespeech);
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                    t1.setLanguage(Locale.ENGLISH);
            }
        });
        generatespeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputEditText.getText().toString();
                t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        // Add listener to clear Morse code output when input text is cleared
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mMorseCodeTextView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        EditText mInputEditText = findViewById(R.id.input_edit_text);
        mMorseCodeTextView = findViewById(R.id.morse_code_text_view);
        View mTranslateButton = findViewById(R.id.translate_button);
        mTranslateButton.setOnClickListener(v -> {
            String inputText = mInputEditText.getText().toString().trim().toLowerCase(Locale.US);
            String morseCode = translateToMorseCode(inputText);
            mMorseCodeTextView.setText(morseCode);
            vibrateMorseCode(morseCode);
        });
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action, keycode;
        action = event.getAction();
        keycode = event.getKeyCode();

        if (keycode == KeyEvent.KEYCODE_VOLUME_UP){
            if(KeyEvent.ACTION_UP == action){
                String intro_text = ("Use the typer to enter the text in the text field, and hit translate to get the morse code equivalent of the text." +
                        "Use the read option to read the text that you typed in the text field.").toString();
                t1.speak(intro_text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        return super.dispatchKeyEvent(event);
    }


}