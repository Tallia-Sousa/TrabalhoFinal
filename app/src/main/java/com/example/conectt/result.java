package com.example.conectt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class result extends AppCompatActivity {

    private TextView logradouroTextView;
    private TextView localidadeTextView;
    private TextView ufTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result); 

        logradouroTextView = findViewById(R.id.logradouroTextView);
        localidadeTextView = findViewById(R.id.localidadeTextView);
        ufTextView = findViewById(R.id.ufTextView);

        Intent intent = getIntent();
        if (intent != null) {
            String logradouro = intent.getStringExtra("logradouro");
            String localidade = intent.getStringExtra("localidade");
            String uf = intent.getStringExtra("uf");

            logradouroTextView.setText("Logradouro: " + logradouro);
            localidadeTextView.setText("Localidade: " + localidade);
            ufTextView.setText("UF: " + uf);
        }
    }
}

