package com.example.campusexpense_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibtHome, ibtInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // EdgeToEdge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);

        // Trang Home là trang chính → nút Home mặc định được chọn
        ibtHome.setSelected(true);
        ibtInfor.setSelected(false);

        // Bấm Home
        ibtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibtHome.setSelected(true);
                ibtInfor.setSelected(false);
            }
        });

        // Bấm Infor
        ibtInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibtHome.setSelected(false);
                ibtInfor.setSelected(true);
                // TODO: chuyển sang Activity Infor
            }
        });
    }
}
