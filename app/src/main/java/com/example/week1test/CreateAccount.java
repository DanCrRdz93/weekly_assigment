package com.example.week1test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    private static final String LOG_TAG = CreateAccount.class.getSimpleName();

    private EditText editEmail;
    private EditText editPass;
    private EditText editRPass;
    private Button btnNext;
    private ArrayList <String> emailsString;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        editEmail = findViewById(R.id.editTxtEmail);
        editPass = findViewById(R.id.editTxtPass);
        editRPass = findViewById(R.id.editTxtRepeatPass);
        btnNext = findViewById(R.id.btnNext);

        emailsString = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail();
                confirmInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
                confirmInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editRPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
                confirmInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void saveData(){
        String newInput = editEmail.getText().toString().trim();

        if (emailsString.contains(newInput)){
            Toast error = Toast.makeText(getApplicationContext(),
                    "This email is already use",
                    Toast.LENGTH_SHORT);
            error.show();
        } else {
            emailsString.add(newInput);
        }
    }

    public boolean validateEmail() {

        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.sad_image);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        // Extract input from EditText
        String emailInput = editEmail.getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            editEmail.setError("Field can not be empty",customErrorDrawable);
            return false;
        } else {

                if (emailsString.contains(emailInput)){
                    editEmail.setError("This email is not available",customErrorDrawable);
                    return false;
                }

                // Matching the input email to a predefined email pattern
                else if (Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    editEmail.setError(null);
                    //mPreferences.edit().putString("EMAIL",emailInput).apply();
                    return true;
                } else {
                    editEmail.setError("Please enter a valid email address",customErrorDrawable);
                    return false;
                }
            }

    }

    private boolean validatePassword() {
        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.sad_image);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        String passwordInput = editPass.getText().toString().trim();
        String passwordRInput = editRPass.getText().toString().trim();

        Log.d(LOG_TAG, passwordInput);
        Log.d(LOG_TAG, passwordRInput);
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            editPass.setError("Field can not be empty",customErrorDrawable);
            return false;
        }

        // if password does not matches to the pattern
        // it will display an error message
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            editPass.setError("Password must have at least 8 characters 1 uppercase and 1 lowercase ",customErrorDrawable);
            return false;
        } else if (!passwordInput.equals(passwordRInput)){
            editPass.setError(null);
            editRPass.setError("Password does not match", customErrorDrawable);
            return false;
        } else {
            editRPass.setError(null);
            return true;
        }
    }

    public void confirmInput() {
        String txtEmail = editEmail.getText().toString().trim();
        String txtPass  = editPass.getText().toString().trim();
        String txtRPass = editRPass.getText().toString().trim();

        if (txtEmail.isEmpty() || txtPass.isEmpty() || txtRPass.isEmpty()) {
            btnNext.setEnabled(false);
        } else {
            if (validateEmail() == true && validatePassword() == true){
                btnNext.setEnabled(true);
            } else {
                btnNext.setEnabled(false);
            }
        }
    }
}