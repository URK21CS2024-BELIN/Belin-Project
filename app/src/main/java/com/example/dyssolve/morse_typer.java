package com.example.dyssolve;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.os.Bundle;

public class morse_typer extends AppCompatActivity {
    private EditText myEditText;
    private Button myButton;
    private Button myButton2;
    private Vibrator vibrator;
    private int countMyButton = 0;
    private int countMyButton2 = 0;
    private boolean isButton2Clicked = false;
    private TextView myTextView;
    TextToSpeech t1;

    // Morse code conversion map
    private Map<String, String> morseToTextMap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //following three lines to hide the top bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_morse_typer);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });


        myEditText = findViewById(R.id.my_edit_text);
        myTextView = findViewById(R.id.my_text_view);



        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        myButton = findViewById(R.id.my_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countMyButton == 7 && isButton2Clicked) {
                    Toast.makeText(morse_typer.this, "Backspace clicked 8 times", Toast.LENGTH_SHORT).show();
                    countMyButton = 0;
                } else {
                    myEditText.append(".");
                    shortVibrate();
                    countMyButton++;
                    String text = myTextView.getText().toString();
                    t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                countMyButton2 = 0;
                // Convert Morse code to text and set to TextView
                String morseCode = myEditText.getText().toString();
                String text = convertMorseToText(morseCode);
                myTextView.setText(text);
            }
        });

        myButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // delete last character
                int len = myEditText.getText().length();
                if (len >= 1) {
                    myEditText.getText().delete(len - 1, len);
                }
                Toast.makeText(morse_typer.this, "Backspace long pressed", Toast.LENGTH_SHORT).show();
                // Convert Morse code to text and set to TextView
                String morseCode = myEditText.getText().toString();
                String text = convertMorseToText(morseCode);
                myTextView.setText(text);
                String text2 = myTextView.getText().toString();
                t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        myButton2 = findViewById(R.id.my_button2);
        myButton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isButton2Clicked) {
                    // add space
                    myEditText.append("  ");
                    Toast.makeText(morse_typer.this, "Double space clicked", Toast.LENGTH_SHORT).show();
                    // Convert Morse code to text and set to TextView
                    String morseCode = myEditText.getText().toString();
                    String text = convertMorseToText(morseCode);
                    // Add space to the text view
                    myTextView.setText(addSpaces(text));
                    String text2 = myTextView.getText().toString();
                    t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
                    return true;
                } else {
                    return false;
                }
            }
        });



        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countMyButton2 == 7) {
                    myEditText.append(" ");
                    Toast.makeText(morse_typer.this, "Space clicked 8 times", Toast.LENGTH_SHORT).show();
                    countMyButton2 = 0;
                } else {
                    myEditText.append("_");
                    longVibrate();
                    countMyButton2++;
                    String text = myTextView.getText().toString();
                    t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                countMyButton = 0;
                isButton2Clicked = true;
                // Convert Morse code to text and set to TextView
                String morseCode = myEditText.getText().toString();
                String text = convertMorseToText(morseCode);
                myTextView.setText(text);
                String text2 = myTextView.getText().toString();
                t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        myButton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // add a space
                myEditText.append(" ");
                Toast.makeText(morse_typer.this, "Space long pressed", Toast.LENGTH_SHORT).show();
                // Convert Morse code to text and set to TextView
                String morseCode = myEditText.getText().toString();
                String text = convertMorseToText(morseCode);
                myTextView.setText(text);
                String text2 = myTextView.getText().toString();
                t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });
        // Initialize Morse code conversion map
        morseToTextMap = new HashMap<>();
        morseToTextMap.put("._", "a");
        morseToTextMap.put("_...", "b");
        morseToTextMap.put("_._.", "c");
        morseToTextMap.put("_..", "d");
        morseToTextMap.put(".", "e");
        morseToTextMap.put(".._.", "f");
        morseToTextMap.put("__.", "g");
        morseToTextMap.put("....", "h");
        morseToTextMap.put("..", "i");
        morseToTextMap.put(".___", "j");
        morseToTextMap.put("_._", "k");
        morseToTextMap.put("._..", "l");
        morseToTextMap.put("__", "m");
        morseToTextMap.put("_.", "n");
        morseToTextMap.put("___", "o");
        morseToTextMap.put(".__.", "p");
        morseToTextMap.put("__._", "q");
        morseToTextMap.put("._.", "r");
        morseToTextMap.put("...", "s");
        morseToTextMap.put("_", "t");
        morseToTextMap.put(".._", "u");
        morseToTextMap.put("..._", "v");
        morseToTextMap.put(".__", "w");
        morseToTextMap.put("_.._", "x");
        morseToTextMap.put("_.__", "y");
        morseToTextMap.put("__..", "z");
        morseToTextMap.put(".____", "1");
        morseToTextMap.put("..___", "2");
        morseToTextMap.put("...__", "3");
        morseToTextMap.put("...._", "4");
        morseToTextMap.put(".....", "5");
        morseToTextMap.put("_....", "6");
        morseToTextMap.put("__...", "7");
        morseToTextMap.put("___..", "8");
        morseToTextMap.put("____.", "9");
        morseToTextMap.put("._._._", ".");
        morseToTextMap.put(" __..__", ",");
        morseToTextMap.put(" ..__..", "?");
        morseToTextMap.put("_._.__", "!");
        morseToTextMap.put("___...", ":");
        morseToTextMap.put("_._._.", ";");
        morseToTextMap.put(".____.", "'");
        morseToTextMap.put("_...._", "-");
        morseToTextMap.put("_.._.", "/");
        morseToTextMap.put("____", " ");
        morseToTextMap.put("._...", "&");
        morseToTextMap.put(".__._.", "@");
        morseToTextMap.put("_____", "0");



    }
    //to read using volume up key


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action, keycode;
        action = event.getAction();
        keycode = event.getKeyCode();

        if (keycode == KeyEvent.KEYCODE_VOLUME_UP){
            if(KeyEvent.ACTION_UP == action){
                String intro_text = ("You can use this page to type morse code. " +
                        "The upper part of the screen is for the short vibration," +
                        "and the lower part of the screen is for the long vibration." +
                        "If you long press the upper part of the screen, it will delete one character of the morse code you entered." +
                        "If you long press the lower part of the screen, it will go to the next morse code.").toString();
                t1.speak(intro_text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    // Convert Morse code to text
    private String convertMorseToText(String morseCode) {
        StringBuilder textBuilder = new StringBuilder();
        String[] words = morseCode.trim().split(" {3}");
        for (String word : words) {
            String[] letters = word.split(" ");
            for (String letter : letters) {
                String text = morseToTextMap.get(letter);
                if (text != null) {
                    textBuilder.append(text);
                }
            }
            textBuilder.append(" ");
        }
        return textBuilder.toString();
    }


    // Short vibration
    private void shortVibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(100);
        }
    }

    // Long vibration
    private void longVibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(400);
        }
    }

    private String addSpaces(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            builder.append(text.charAt(i)).append(" ");
        }
        return builder.toString().trim();
    }




}