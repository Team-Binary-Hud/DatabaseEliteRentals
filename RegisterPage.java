package com.example.eliterental;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText rePassword;
    private EditText phoneNumber;
    private EditText postCode;
    Button registerButton;
    int credentials;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        name = findViewById(R.id.RegisterName);
        email = findViewById(R.id.RegisterEmail);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.RegisterPassword);
        rePassword = findViewById(R.id.RegisterRePassword);
        registerButton = findViewById(R.id.RegistrationButton);
        postCode = findViewById(R.id.addressPostCode);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                  credentialChecker();


                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean passwordStringCheck() {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean specialChar = false;
        for (int i = 0; i < password.getText().length(); i++) {
            ch = password.getText().charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            } else if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) {
                specialChar = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag && specialChar)
                return true;
        }
        return false;
    }

    private boolean emailStringCheck() {
        boolean email = false;

        String emailText = this.email.getText().toString();
        if (!emailText.isEmpty() && !emailText.startsWith("@") && !emailText.endsWith("@") && emailText.contains("@") &&
                emailText.length() > 6 && emailText.endsWith("gmail.com") || emailText.endsWith(".co.uk")) {
            email = true;
        }

        return email;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean nameStringCheck() {
        boolean name = true;
        char ch;
        int whiteSpaces = 0;

        for (int i = 0; i < this.name.getText().length(); i++) {
            ch = this.name.getText().charAt(i);
            if (Character.isDigit(ch))
                name = false;
            else if (!Character.isAlphabetic(ch) && !Character.isDigit(ch) && !Character.isWhitespace(ch))
                name = false;
            else if (Character.isWhitespace(ch))
                whiteSpaces++;
        }
        return name && whiteSpaces <= 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean phoneStringCheck() {
        boolean phoneN = true;
        char ch;
        int phoneNumLength = 0;

        for (int i = 0; i < this.phoneNumber.getText().length(); i++) {
            ch = this.phoneNumber.getText().charAt(i);
            if (Character.isAlphabetic(ch))
                phoneN = false;
            else if (!Character.isAlphabetic(ch) && !Character.isDigit(ch) && !Character.isWhitespace(ch))
                phoneN = false;
            else if (Character.isWhitespace(ch))
                phoneN = false;
            else if (Character.isDigit(ch))
                phoneNumLength++;
        }
        return phoneN && phoneNumLength == 10;
    }

    private boolean postCodeCheck() {
        String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postCode.getText().toString());

       return matcher.matches();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void credentialChecker() {

        Intent intentLogin = new Intent(this, LoginPage.class);
        if (!nameStringCheck() || name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Not a valid name", Toast.LENGTH_SHORT).show();
        } else if (!phoneStringCheck() || phoneNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "Not a valid Number", Toast.LENGTH_SHORT).show();
        } else if(!postCodeCheck()){
            Toast.makeText(this, "Not a valid Post Code", Toast.LENGTH_SHORT).show();
        }  else if (!emailStringCheck()) {
            Toast.makeText(this, "Not a valid email", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().length() < 8) {
            Toast.makeText(this, "Password needs to be at least 8 characters", Toast.LENGTH_SHORT).show();
        } else if (!passwordStringCheck()) {
            Toast.makeText(this, "password needs at least:\nOne Uppercase:One Lowercase:\nOne Special Character:One Number", Toast.LENGTH_LONG).show();
        } else if (!(password.getText().toString().equals(rePassword.getText().toString()))) {
            Toast.makeText(this, "Password entered did not match", Toast.LENGTH_SHORT).show();
        } else
            {String addName=name.getText().toString();
            String addPhone=phoneNumber.getText().toString();
            String addEmail=email.getText().toString();
            String addPostCode=postCode.getText().toString();
            String addPassword=password.getText().toString();
            String addRePassword=rePassword.getText().toString();
            String commandChosen="register";
            BackgroundTask backgroundTask=new BackgroundTask(getApplicationContext());
            backgroundTask.execute(commandChosen,addName,addPhone,addEmail,addPostCode,addPassword,addRePassword);
                startActivity(intentLogin);}
    }
}