package com.example.lr_8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_PERMISSION_CODE = 100;
    private static final int CONTACTS_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSecond = findViewById(R.id.btn_second_activity);
        Button btnCall = findViewById(R.id.btn_call);
        Button btnContacts = findViewById(R.id.btn_contacts);

        // Переход во второе активити
        btnSecond.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        // Звонок
        btnCall.setOnClickListener(v -> checkPermission(Manifest.permission.CALL_PHONE, CALL_PERMISSION_CODE));

        // Чтение контактов
        btnContacts.setOnClickListener(v -> checkPermission(Manifest.permission.READ_CONTACTS, CONTACTS_PERMISSION_CODE));
    }

    private void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == CALL_PERMISSION_CODE) makeCall();
            else if (requestCode == CONTACTS_PERMISSION_CODE) readContacts();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    private void makeCall() {
        // Совершаем звонок по номеру
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:123456789"));
        try {
            startActivity(callIntent);
        } catch (SecurityException e) {
            Toast.makeText(this, "Нет разрешения на звонок", Toast.LENGTH_SHORT).show();
        }
    }

    private void readContacts() {
        // Чтение контактов
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        if (cursor != null) {
            int count = cursor.getCount();
            StringBuilder sb = new StringBuilder("Найдено контактов: " + count + "\n\n");
            int limit = 0;
            while (cursor.moveToNext() && limit < 5) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                sb.append(name).append(": ").append(phone).append("\n");
                limit++;
            }
            cursor.close();
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Нет доступа к контактам", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == CALL_PERMISSION_CODE) makeCall();
            else if (requestCode == CONTACTS_PERMISSION_CODE) readContacts();
        } else {
            Toast.makeText(this, "Разрешение отклонено", Toast.LENGTH_SHORT).show();
        }
    }
}