package com.example.conectt;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.botaoN);

        button.setOnClickListener(this::navigate);


    }
    private void navigate(View view){
        Intent intent = new Intent(MainActivity.this, BuscaActivity.class);
        startActivity(intent);
    }


}