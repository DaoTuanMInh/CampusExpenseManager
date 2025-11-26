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

        btnCreateAccount.setOnClickListener( v -> {
            String username = edtRegisterUsername.getText().toString();
            String fullname = edtRegisterFullName.getText().toString();
            String password = edtRegisterPassword.getText().toString();
            String email = edtRegisterEmail.getText().toString();
            String phonenumber = edtRegisterPhonenumber.getText().toString().trim();
            User user = new User(0, username, fullname, password, email, phonenumber);
            try{
                DatabaseHelper db = new DatabaseHelper(this);
                long resultId = db.addUser(user);
                if(resultId > 0){
                    Toast.makeText(this, "Create Account Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Create Account failed", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
