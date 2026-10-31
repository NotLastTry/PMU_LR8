package com.example.lr_8;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView tvLifecycle;
    private StringBuilder logBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvLifecycle = findViewById(R.id.tv_lifecycle);
        Button btnBack = findViewById(R.id.btn_back);

        // При возврате уничтожаем активити
        btnBack.setOnClickListener(v -> finish());

        appendLog("onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        appendLog("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        appendLog("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        appendLog("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        appendLog("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appendLog("onDestroy()");
        // В реальном приложении здесь можно сохранить лог, например, в SharedPreferences
    }

    private void appendLog(String methodName) {
        logBuilder.append(methodName).append("\n");
        tvLifecycle.setText(logBuilder.toString());
    }
}