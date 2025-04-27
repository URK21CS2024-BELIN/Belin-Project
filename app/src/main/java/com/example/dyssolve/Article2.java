package com.example.dyssolve;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

import android.os.Bundle;

public class Article2 extends AppCompatActivity {
    String Morse_text="... --- .--. .... .. .- / .- -- --- .-. ..- ... --- / .. ... / .- -. / .- -- . .-. .. -.-. .- -. / -... ..- ... .. -. . ... ... .-- --- -- .- -. / .-- .... --- / .-- .- ... / - .... . / ..-. --- .-. -- . .-. / -.-. . --- / --- ..-. / -. .- ... - -.-- / --. .- .-.. / .- -. -.. / ..-. --- ..- -. -.. . .-. / --- ..-. / --. .. .-. .-.. -... --- ... ... .-.-.- / --. .-. --- .-- .. -. --. / ..- .--. --..-- / ... .... . / .-- .- ... -. .-..-. - / .- / ...- . .-. -.-- / .... .- .--. .--. -.-- / -.- .. -.. .-.-.- / -.. --- -.-. - --- .-. ... / - .... --- ..- --. .... - / ... .... . / .... .- -.. / - --- ..- .-. . - - . .-..-. ... / ... -.-- -. -.. .-. --- -- . .-.-.- / .-.. .- - . .-. --..-- / ... .... . / .-- .- ... / -.. .. .- --. -. --- ... . -.. / .-- .. - .... / .- - - . -. - .. --- -. / -.. . ..-. .. -.-. .. - / .... -.-- .--. . .-. .- -.-. - .. ...- .. - -.-- / -.. .. ... --- .-. -.. . .-. / -.--. .- -.. .... -.. -.--.- / .- -. -.. / -.. . .--. .-. . ... ... .. --- -. .-.-.- / .... --- .-- . ...- . .-. --..-- / -. --- -. . / --- ..-. / - .... .. ... / .... .. -. -.. . .-. . -.. / .... . .-. / .-- .. .-.. .-.. .-.-.- / ... .... . / --- .--. . -. . -.. / .- / .-.. . -- --- -. .- -.. . / ... - .- -. -.. / .- - / ----. / -.-- . .- .-. ... / .- -. -.. / .-. . .- -.. / -- .- -. -.-- / -... --- --- -.- ... / .- -... --- ..- - / ... - .- .-. - ..- .--. ... / .- ... / .- / - . . -. .- --. . .-. .-.-.- / .. -. / .- -.. -.. .. - .. --- -. --..-- / ... .... . / -.. .-. --- .--. .--. . -.. / --- ..- - / --- ..-. / -.-. --- .-.. .-.. . --. . / -... ..- - / .... . .-.. -.. / .- / ..-. ..- .-.. .-.. -....- - .. -- . / .--- --- -... .-.-.- / -... -.-- / - .... . / .- --. . / --- ..-. / ..--- ..--- --..-- / ... .... . / .... .- -.. / .-- --- .-. -.- . -.. / .---- ----- / -.. .. ..-. ..-. . .-. . -. - / .--- --- -... ... .-.-.- / ... .... . / --- .--. . -. . -.. / .- -. / . -... .- -.-- / ... .... --- .--. / - --- / .-- --- .-. -.- / .. -. / .... . .-. / ... .--. .- .-. . / - .. -- . / .- -. -.. / ... .... .. .--. .--. . -.. / .... . .-. / .--. .-. --- -.. ..- -.-. - ... / .-- .. - .... / - .... . / - .. .--. ... / ... .... . / . .- .-. -. . -.. / .. -. / .--. .... --- - --- --. .-. .- .--. .... -.-- / -.-. .-.. .- ... ... .-.-.- / --- ...- . .-. / - .. -- . --..-- / ... .... . / -.. . ...- . .-.. --- .--. . -.. / .- -. / . -.-- . / ..-. --- .-. / .--. .-. --- -.. ..- -.-. - / ... . .-.. . -.-. - .. --- -. .-.-.- / ... .... . / --. --- - / - .-- --- / -.-. .... .- -. . .-.. / .--- .- -.-. -.- . - ... / .- - / .- / -.-- .- .-. -.. / ... .- .-.. . / ..-. --- .-. / # ---.. / .- -. -.. / ... --- .-.. -.. / - .... . -- / --- -. / . -... .- -.-- / ..-. --- .-. / # ...-- ----- ----- ----- .-.-.- / .-.. .- - . .-. / --- -. --..-- / ... .... . / -... ..- .. .-.. - / .... . .-. / --- .-- -. / ... .. - . / .- -. -.. / ... --- .-.. -.. / .... . .-. / .--. .-. --- -.. ..- -.-. - ... / ..-. .-. --- -- / - .... . .-. . .-.-.- / .... . .-. / -.-. --- -- .--. .- -. -.-- / .-- .- ... / -.. --- .. -. --. / .-- . .-.. .-.. --..-- / .- -. -.. / ... .... . / .-- .- ... / --- -. / - .... . / ..-. --- .-. -... . ... / ...-- ----- / ..- -. -.. . .-. / ...-- ----- / .-.. .. ... - .-.-.- / --- ..-. / -.-. --- ..- .-. ... . --..-- / - .... .. -. --. ... / -.-. --- ..- .-.. -.. -. .-..-. - / .- .-.. .-- .- -.-- ... / -... . / ... -- --- --- - .... / ... .. -. -.-. . / ... .... . / .-.. .- -.-. -.- . -.. / - .... . / -- .- -. .- --. . -- . -. - / ... -.- .. .-.. .-.. ... / - --- / .-. ..- -. / - .... . / -.-. --- -- .--. .- -. -.-- --..-- / .- -. -.. / - .... .. -. --. ... / - --- --- -.- / .- / - ..- .-. -. / ..-. --- .-. / - .... . / .-- --- .-. ... . .-.-.- / - .... . / -.-. --- -- .--. .- -. -.-- / . -. - . .-. . -.. / -... .- -. -.- .-. ..- .--. - -.-. -.-- / .--. .-. --- -.-. . . -.. .. -. --. ... # / .-.. .- .-- ... ..- .. - ... / ..-. --- .-.. .-.. --- .-- . -.. .-.-.- / .... . -. -.-. . --..-- / ... .... . / .-. . ... .. --. -. . -.. / .- ... / - .... . / -.-. . --- / -... ..- - / -.- . .--. - / .- / ... . -. .. --- .-. / . -..- . -.-. ..- - .. ...- . / .--. --- ... - .-.-.- / -.. . ... .--. .. - . / .- .-.. .-.. / - .... . ... . / -.. --- .-- -. ..-. .- .-.. .-.. ... --..-- / ... .... . / -.. .. -.. -. .-..-. - / .-.. . - / .... . .-. / ... .. - ..- .- - .. --- -. / -.. .- -- .--. . -. / .... . .-. / ... .--. .. .-. .. - ... .-.-.- / ... .... . / .--. ..- -... .-.. .. ... .... . -.. / .... . .-. / .- ..- - --- -... .. --- --. .-. .- .--. .... -.-- / - .. - .-.. . -.. / # --. .. .-. .-.. -... --- ... ... .-.-.- / - .... . / -... --- --- -.- / .-- .- ... / --- -. / - .... . / -. . .-- / -.-- --- .-. -.- / - .. -- . ... / -... . ... - ... . .-.. .-.. . .-. / .-.. .. ... - / ..-. --- .-. / .---- ---.. / .-- . . -.- ... / ... - .-. .- .. --. .... - -.-.-- / .... . .-. / ... - --- .-. -.-- / .... .- ... / -- .- -. -.-- / -- --- .-. . / - .-- .. ... - ... / .- -. -.. / - ..- .-. -. ... --..-- / -... ..- - / - .... . -.-- / .- .-.. .-.. / - . .-.. .-.. / ..- ... / - .... . / ... .- -- . / - .... .. -. --. .-.-.- / -. . ...- . .-. / --. .. ...- . / ..- .--. .-.-.- / -- --- - .. ...- .- - .. --- -. .- .-.. / ... - --- .-. .. . ... / - .- -.- . .- .-- .- -.-- / ... ..- -.-. -.-. . ... ... / -.-. .- -. / ... --- -- . - .. -- . ... / --. . - / - --- / -.-- --- ..- .-. / .... . .- -.. --..-- / .- -. -.. / -.-- --- ..- / -- .- -.-- / .-.. --- ... . / -.-- --- ..- .-. / .-- .- -.-- .-.-.- / .. - .-..-. ... / .. -- .--. --- .-. - .- -. - / - --- / .--. .-. .. --- .-. .. - .. --.. . / -.-- --- ..- .-. / --. --- .- .-.. ... / .- -. -.. / .-.. . .- .-. -. / .-- .... --- / -.-- --- ..- / .- .-. . .-.-.- / -... ..- .. .-.. -.. .. -. --. / -.-- --- ..- .-. / --- .-- -. / .. -.. . -. - .. - -.-- / .. -. / .- / .-- --- .-. .-.. -.. / .-- .... . .-. . / .--. . --- .--. .-.. . / .-.. --- ...- . / .--. ..- - - .. -. --. / - .- --. ... / .. ... / -.. .. ..-. ..-. .. -.-. ..- .-.. - .-.-.- / .-- .... . -. / -.-- --- ..- / -.- -. --- .-- / .- -. -.. / ..- -. -.. . .-. ... - .- -. -.. / -.-- --- ..- .-. / ... - .-. . -. --. - .... ... / .- -. -.. / .-- . .- -.- -. . ... ... . ... --..-- / - .... --- ... . / - .- --. ... / .-- .. .-.. .-.. / -. --- - / .- ..-. ..-. . -.-. - / -.-- --- ..- / -. . --. .- - .. ...- . .-.. -.-- .-.-.-";

    private TextView textView1, textView2;
    private Button button;
    private HashMap<Character, String> morseMap = new HashMap<>();
    TextToSpeech t1;
    Button read_bt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_article2);



        textView1 = findViewById(R.id.textView3);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        read_bt = findViewById(R.id.button2);

        // initialize morse code map
        morseMap.put('a', ".-");
        morseMap.put('b', "-...");
        morseMap.put('c', "-.-.");
        morseMap.put('d', "-..");
        morseMap.put('e', ".");
        morseMap.put('f', "..-.");
        morseMap.put('g', "--.");
        morseMap.put('h', "....");
        morseMap.put('i', "..");
        morseMap.put('j', ".---");
        morseMap.put('k', "-.-");
        morseMap.put('l', ".-..");
        morseMap.put('m', "--");
        morseMap.put('n', "-.");
        morseMap.put('o', "---");
        morseMap.put('p', ".--.");
        morseMap.put('q', "--.-");
        morseMap.put('r', ".-.");
        morseMap.put('s', "...");
        morseMap.put('t', "-");
        morseMap.put('u', "..-");
        morseMap.put('v', "...-");
        morseMap.put('w', ".--");
        morseMap.put('x', "-..-");
        morseMap.put('y', "-.--");
        morseMap.put('z', "--..");

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textView1.getText().toString().toLowerCase();
                StringBuilder morseCode = new StringBuilder();

                // convert text to morse code
                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    if (morseMap.containsKey(c)) {
                        morseCode.append(morseMap.get(c)).append(" ");

                    }
                }

                textView2.setText(morseCode.toString());

                vibrateMorseCode(Morse_text);
            }
        });
        read_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text2 = textView1.getText().toString();
                t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            }
        });}
    public void vibrateMorseCode(String Morse_text) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final long dotDuration = 200; // in milliseconds
        final long dashDuration = 3 * dotDuration;
        final long spaceDuration = 1000;
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Morse_text.length(); i++) {
                    final char c = Morse_text.charAt(i);
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
}