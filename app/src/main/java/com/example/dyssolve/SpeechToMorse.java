package com.example.dyssolve;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.speech.tts.TextToSpeech;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SpeechToMorse extends Activity {

    private EditText textInput;
    private TextView textOutput;
    private Button speechButton;
    private Button saveButton;
    private String outputFile;

    private Button morseButton;

    private static final String MORSE_DICTIONARY = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String[] MORSE_VALUES = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", "-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----."};




    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextToSpeech t1;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_speech_to_morse);


        textInput = findViewById(R.id.textInput);
        textOutput = findViewById(R.id.textOutput);
        speechButton = findViewById(R.id.speechButton);
        saveButton = findViewById(R.id.saveButton);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                    t1.setLanguage(Locale.ENGLISH);
            }
        });

        outputFile = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/output.txt";


        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOutput();
            }

        });
        morseButton = findViewById(R.id.morseButton);
        morseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textInput.getText().toString();
                String morse = toMorseCode(input);
                textOutput.setText(morse);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    VibrationEffect vibrationEffect = VibrationEffect.createWaveform(getMorseVibrationPattern(morse), -1);
                    vibrator.vibrate(vibrationEffect);
                } else {
                    // Deprecated in API 26
                    vibrator.vibrate(getMorseVibrationPattern(morse), -1);
                }
            }
        });

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(),
                    "Speech recognition not supported on this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private static final int SAVE_FILE_REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = result.get(0);
            String morseText = toMorseCode(spokenText);
            textInput.setText(spokenText);
            textOutput.setText(morseText);
        }

        if (requestCode == SAVE_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            try {
                OutputStream outputStream = getContentResolver().openOutputStream(uri);
                String outputText = textInput.getText().toString();
                outputStream.write(outputText.getBytes());
                outputStream.close();
                Toast.makeText(getApplicationContext(),
                        "Output saved to " + uri.toString(),
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),
                        "Error saving output file.",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
    private String toMorseCode(String input) {
        input = input.toLowerCase(Locale.getDefault());
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                output.append("   "); // 3 spaces between words
            } else {
                int index = MORSE_DICTIONARY.indexOf(c);
                if (index >= 0) {
                    output.append(MORSE_VALUES[index]).append(" ");
                }
            }
        }
        return output.toString();
    }

    private void vibrate(long[] pattern) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createWaveform(pattern, -1);
            vibrator.vibrate(effect);
        } else {
            vibrator.vibrate(pattern, -1);
        }
    }

    private long[] getMorseVibrationPattern(String morse) {
        List<Long> pattern = new ArrayList<>();
        for (int i = 0; i < morse.length(); i++) {
            char c = morse.charAt(i);
            if (c == '.') {
                pattern.add(100L); // Dot
                pattern.add(100L); // Pause between dots and dashes
            } else if (c == '-') {
                pattern.add(1000L); // Dash
                pattern.add(150L); // Pause between dots and dashes
            } else if (Character.isWhitespace(c)) {
                pattern.add(0L); // Pause between letters
                pattern.add(200L); // Pause between words
            } else {
                throw new IllegalArgumentException("Invalid character in Morse code: " + c);
            }
        }
        long[] patternArray = new long[pattern.size()];
        for (int i = 0; i < pattern.size(); i++) {
            patternArray[i] = pattern.get(i);
        }
        return patternArray;
    }

    private void saveOutput() {
        String outputText = textOutput.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(SpeechToMorse.this);
        builder.setTitle("Save Output");
        builder.setMessage("Enter file name:");

        final EditText input = new EditText(SpeechToMorse.this);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = input.getText().toString().trim();
                if (!fileName.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TITLE, fileName);
                    startActivityForResult(intent, SAVE_FILE_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter a file name.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

        textOutput.setText(outputText);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action, keycode;
        action = event.getAction();
        keycode = event.getKeyCode();

        if (keycode == KeyEvent.KEYCODE_VOLUME_UP){
            if(KeyEvent.ACTION_UP == action){
                String intro_text = ("Hey there! Click on the speech button to start speech recognition. Stop speaking to stop the speech recognition. Click on the read morse code option. To read the speech in morse code through vibrations. Click on the save and type the file name to save the file in the desired location.").toString();
                t1.speak(intro_text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        return super.dispatchKeyEvent(event);
    }


}
