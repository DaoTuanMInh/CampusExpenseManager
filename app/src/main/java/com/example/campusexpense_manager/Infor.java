package com.example.campusexpense_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Infor extends AppCompatActivity {
    ImageButton ibtHome, ibtInfor;
    TextView txtInforName, txtInforEmail, txtInforPhonenumber;
    DatabaseHelper db;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_infor);


        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);
        txtInforName = findViewById(R.id.txtInforName);
        txtInforEmail = findViewById(R.id.txtInforEmail);
        txtInforPhonenumber = findViewById(R.id.txtInforPhonenumber);

//        ibtHome.setSelected(false);
//        ibtInfor.setSelected(true);

//        ibtInfor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ibtHome.setSelected(false);
//                ibtInfor.setSelected(true);
//            }
//        });
        ibtHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        db = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username != null) {
            User user = db.getUserByUsername(username);

            if (user != null) {
                txtInforName.setText(user.getFullname());
                txtInforEmail.setText(user.getEmail());
                txtInforPhonenumber.setText(user.getPhonenumber());
            }
        }
    }



}