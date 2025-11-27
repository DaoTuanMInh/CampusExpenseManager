package com.example.campusexpense_manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    Button btnCreateAccount;
    EditText edtRegisterUsername, edtRegisterFullName,edtRegisterPassword, edtRegisterEmail, edtRegisterPhonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        edtRegisterUsername = findViewById(R.id.edtRegisterUsername);
        edtRegisterFullName = findViewById(R.id.edtRegisterFullName);
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword);
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail);
        edtRegisterPhonenumber = findViewById(R.id.edtRegisterPhonenumber);

        btnCreateAccount.setOnClickListener(v -> {
            String username = edtRegisterUsername.getText().toString().trim();
            String fullname = edtRegisterFullName.getText().toString().trim();
            String password = edtRegisterPassword.getText().toString().trim();
            String email = edtRegisterEmail.getText().toString().trim().toLowerCase();
            String phonenumber = edtRegisterPhonenumber.getText().toString().trim();
            if (username.isEmpty() || fullname.isEmpty() || password.isEmpty() || email.isEmpty() || phonenumber.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!phonenumber.matches("\\d{10}")) {
                Toast.makeText(this, "Phone number must contain exactly 10 digits!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!email.endsWith("@gmail.com") || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid Gmail address (example@gmail.com)", Toast.LENGTH_SHORT).show();
                return;
            }
            DatabaseHelper db = new DatabaseHelper(this);
            if (db.isPhoneExists(phonenumber)) {
                Toast.makeText(this, "This phone number is already registered!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (db.isEmailExists(email)) {
                Toast.makeText(this, "This email is already in use!", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(0, username, fullname, password, email, phonenumber);
            long resultId = db.addUser(user);

            if (resultId > 0) {
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Prevent going back to register screen
            } else {
                Toast.makeText(this, "Registration failed. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
