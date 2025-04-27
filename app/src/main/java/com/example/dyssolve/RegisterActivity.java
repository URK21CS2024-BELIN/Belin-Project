package com.example.dyssolve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dyssolve.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseDatabase db;

    Button btnRegister;
    TextView tvLoginHere;
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextInputEditText etUserName;
    TextInputEditText etSchool;
    TextInputEditText etPhone;

    private TextInputLayout get_birth_date;
    private TextInputEditText show_date_of_brith;
    String[] items = {"Visually Challenged", "Hearing-impaired", "Unable To Speak",
            "Both: Hearing-impaired & Unable To Speak", "Both: Visually Challenged & Hearing-impaired"};
    AutoCompleteTextView drop_items;
    ArrayAdapter<String> adapterItems;
    String selectedCategory = " ";

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //following three lines to hide the top bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etUserName = findViewById(R.id.etUserName);
        etSchool = findViewById(R.id.etSchool);
        etPhone = findViewById(R.id.etPhone);

        mAuth = FirebaseAuth.getInstance();

        get_birth_date = findViewById(R.id.get_birth_date);
        show_date_of_brith = findViewById(R.id.show_date_of_brith);
        drop_items = findViewById(R.id.drop_items);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        show_date_of_brith.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        show_date_of_brith.setText(date);
                        //tvSelectDate.setText(date);

                    }
                },year, month,day);
                dialog.show();

            }
        });

        adapterItems = new ArrayAdapter<String>(this, R.layout.category_items, items);
        drop_items.setAdapter(adapterItems);
        drop_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedCategory = item;
            }
        });

        btnRegister.setOnClickListener(view ->{
            createUser();
        });
        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

    }

    private void createUser() {
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();
        String user_name = etUserName.getText().toString();
        String dob = show_date_of_brith.getText().toString();
        String drop_items = selectedCategory;
        String school = etSchool.getText().toString();
        String phone = etPhone.getText().toString();


        if (TextUtils.isEmpty(user_name)) {
            etUserName.setError("Enter your Name");
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(dob)) {
            show_date_of_brith.setError("Select your date of birth");
            show_date_of_brith.requestFocus();
        } else if (TextUtils.isEmpty(school)) {
            etSchool.setError("Enter Your School name");
            etSchool.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Enter Your Phone number");
            etPhone.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "User registered successfully... Please verify your Email address...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                                    Users users = new Users(user_name, dob, drop_items, school, phone, password, email);

                                    db = FirebaseDatabase.getInstance();
                                    reference = db.getReference("Users");
                                    reference.child(user_name).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            etUserName.setText("");
                                            show_date_of_brith.setText("");
                                            etSchool.setText("");
                                            etPhone.setText("");
                                            etRegPassword.setText("");
                                            etRegEmail.setText("");
                                        }
                                    });

                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "Registration Failed: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                }
                            }

                        });

                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Failed: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}