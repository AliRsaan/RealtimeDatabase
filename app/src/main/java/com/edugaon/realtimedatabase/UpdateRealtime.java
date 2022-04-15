package com.edugaon.realtimedatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
public class UpdateRealtime extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_realtime);
        MaterialTextView getName = findViewById(R.id.getName);
        MaterialTextView getEmail = findViewById(R.id.getEmail);
        MaterialTextView getPassword = findViewById(R.id.getPassword);
        MaterialTextView getMobileNumber = findViewById(R.id.getMobileNumber);
    }
}