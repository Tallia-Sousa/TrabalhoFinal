package com.example.conectt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity {

    private TextView logradouroTextView;
    private TextView localidadeTextView;
    private TextView ufTextView;
    private TextView bairroTextView;
    private ImageView Bvoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        logradouroTextView = findViewById(R.id.logradouroTextView);
        localidadeTextView = findViewById(R.id.localidadeTextView);
        ufTextView = findViewById(R.id.ufTextView);
        bairroTextView = findViewById(R.id.bairroTextView);
        Bvoltar = findViewById(R.id.voltarBu);


        Bvoltar.setOnClickListener(this::navigate);

        Intent intent = getIntent();
        if (intent != null) {
            String logradouro = intent.getStringExtra("logradouro");
            String localidade = intent.getStringExtra("localidade");
            String uf = intent.getStringExtra("uf");
            String bairro = intent.getStringExtra("bairro");

            logradouroTextView.setText("Logradouro: " + logradouro);
            localidadeTextView.setText("Localidade: " + localidade);
            ufTextView.setText("UF: " + uf);
            bairroTextView.setText("Bairro:" +bairro);

        }
    }

    public void navigate(View view){
        Intent intent = new Intent(this,BuscaActivity.class);
        startActivity(intent);
    }
}