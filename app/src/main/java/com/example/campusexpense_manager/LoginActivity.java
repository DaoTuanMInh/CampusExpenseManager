package com.example.campusexpense_manager;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    EditText edtUsername, edtPassword;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferensesEditor = sharedPreferences.edit();

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnRegister.setOnClickListener(v ->{
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v->{
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            try{
                DatabaseHelper db = new DatabaseHelper(this);
                User user = db.getUserByUsernameAndPasword(username, password);
                if(user != null && user.getEmail() != null){
                    AppData.isLogin = true;
                    sharedPreferensesEditor.putString("usernam", username);
                    sharedPreferensesEditor.putString("emai", user.getEmail());
                    sharedPreferensesEditor.putLong("user_id", user.getId());
                    sharedPreferensesEditor.putBoolean("isLogin", true);
                    sharedPreferensesEditor.apply();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }catch (Exception e){
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
