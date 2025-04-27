package com.example.dyssolve;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Pair;

import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

import android.os.Bundle;

public class PdfToMorse extends AppCompatActivity {

    private static final int PICK_PDF_FILE = 1;
    private TextView morseCodeTextView;
    TextToSpeech t1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_pdf_to_morse);

        morseCodeTextView = findViewById(R.id.morse_code_text);
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                    t1.setLanguage(Locale.ENGLISH);
            }
        });

        Button selectFileButton = findViewById(R.id.select_file_button);
        selectFileButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, PICK_PDF_FILE);
            }
        });

        Button vibrateButton = findViewById(R.id.vibrate_button);
        vibrateButton.setOnClickListener(view -> {
            String morseCode = morseCodeTextView.getText().toString().replaceAll("[^\\.\\-\\s]", "");
            vibrateMorseCode(morseCode);
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action, keycode;
        action = event.getAction();
        keycode = event.getKeyCode();

        if (keycode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (KeyEvent.ACTION_UP == action) {
                String intro_text = ("You can use this page to upload your pdf file, and it will convert the contents in the file, to morse code.").toString();
                t1.speak(intro_text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        return super.dispatchKeyEvent(event);
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_FILE && resultCode == RESULT_OK && data != null) {
            Uri pdfUri = data.getData();
            Pair<String, String> result = extractMorseCodeFromPdf(pdfUri);
            morseCodeTextView.setText("Text: " + result.first + "\nMorse code: " + result.second);
        }
    }

    private Pair<String, String> extractMorseCodeFromPdf(Uri pdfUri) {
        String text = "";
        String morseCode = "";
        try {
            InputStream inputStream = getContentResolver().openInputStream(pdfUri);
            PdfReader reader = new PdfReader(inputStream);
            int numPages = reader.getNumberOfPages();
            StringBuilder textBuilder = new StringBuilder();
            StringBuilder morseCodeBuilder = new StringBuilder();
            for (int i = 1; i <= numPages; i++) {
                String pageText = PdfTextExtractor.getTextFromPage(reader, i);
                textBuilder.append(pageText);
                morseCodeBuilder.append(toMorseCode(pageText)).append(" ");
            }
            text = textBuilder.toString();
            morseCode = morseCodeBuilder.toString();
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair<>(text, morseCode);
    }

    private String toMorseCode(String text) {
        StringBuilder sb = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (MORSE_CODE.containsKey(c)) {
                sb.append(MORSE_CODE.get(c)).append(" ");
            }
        }
        return sb.toString();
    }

    private static final HashMap<Character, String> MORSE_CODE = new HashMap<Character, String>();

    static {
        MORSE_CODE.put('A', ".-");
        MORSE_CODE.put('B', "-...");
        MORSE_CODE.put('C', "-.-.");
        MORSE_CODE.put('D', "-..");
        MORSE_CODE.put('E', ".");
        MORSE_CODE.put('F', "..-.");
        MORSE_CODE.put('G', "--.");
        MORSE_CODE.put('H', "....");
        MORSE_CODE.put('I', "..");
        MORSE_CODE.put('J', ".---");
        MORSE_CODE.put('K', "-.-");
        MORSE_CODE.put('L', ".-..");
        MORSE_CODE.put('M', "--");
        MORSE_CODE.put('N', "-.");
        MORSE_CODE.put('O', "---");
        MORSE_CODE.put('P', ".--.");
        MORSE_CODE.put('Q', "--.-");
        MORSE_CODE.put('R', ".-.");
        MORSE_CODE.put('S', "...");
        MORSE_CODE.put('T', "-");
        MORSE_CODE.put('U', "..-");
        MORSE_CODE.put('V', "...-");
        MORSE_CODE.put('W', ".--");
        MORSE_CODE.put('X', "-..-");
        MORSE_CODE.put('Y', "-.--");
        MORSE_CODE.put('Z', "--..");
        MORSE_CODE.put('0', "-----");
        MORSE_CODE.put('1', ".----");
        MORSE_CODE.put('2', "..---");
        MORSE_CODE.put('3', "...--");
        MORSE_CODE.put('4', "....-");
        MORSE_CODE.put('5', ".....");
        MORSE_CODE.put('6', "-....");
        MORSE_CODE.put('7', "--...");
        MORSE_CODE.put('8', "---..");
        MORSE_CODE.put('9', "----.");
        MORSE_CODE.put('.', ".-.-.-");
        MORSE_CODE.put(',', "--..--");
        MORSE_CODE.put('?', "..--..");
        MORSE_CODE.put('\'', ".----.");
        MORSE_CODE.put('!', "-.-.--");
        MORSE_CODE.put('/', "-..-.");
        MORSE_CODE.put('(', "-.--.");
        MORSE_CODE.put(')', "-.--.-");
        MORSE_CODE.put('&', ".-...");
        MORSE_CODE.put(':', "---...");
        MORSE_CODE.put(';', "-.-.-.");
        MORSE_CODE.put('=', "-...-");
        MORSE_CODE.put('+', ".-.-.");
        MORSE_CODE.put('-', "-....-");
        MORSE_CODE.put('_', "..--.-");
        MORSE_CODE.put('"', ".-..-.");
        MORSE_CODE.put('$', "...-..-");
        MORSE_CODE.put('@', ".--.-.");
        MORSE_CODE.put(' ', "/");
    }

}