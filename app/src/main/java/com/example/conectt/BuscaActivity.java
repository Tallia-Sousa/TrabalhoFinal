package com.example.conectt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conectt.service.Endereco;
import com.example.conectt.service.RetrofitClient;
import com.example.conectt.service.viaCepService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BuscaActivity extends AppCompatActivity {

    private EditText cepInput;
    private Button buscarButton;

    private ImageView voltar;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca);

        cepInput = findViewById(R.id.cepInput);
        buscarButton = findViewById(R.id.buscarButton);

        voltar = findViewById(R.id.voltar);


        voltar.setOnClickListener(this::navigate);


        buscarButton.setOnClickListener(v -> {
            String cep = cepInput.getText().toString().trim();
            if (!cep.isEmpty()) {
                buscarCep(cep);
            } else {
                Toast.makeText(BuscaActivity.this, "Por favor, insira um CEP", Toast.LENGTH_LONG).show();
            }
        });
    }



    private void buscarCep(String cep) {
        Retrofit retrofit = RetrofitClient.getClient("https://viacep.com.br/ws/");
        viaCepService service = retrofit.create(viaCepService.class);
        Call<Endereco> call = service.getEndereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    if (endereco != null) {
                        Intent intent = new Intent(BuscaActivity.this, Result.class);
                        intent.putExtra("logradouro", endereco.getLogradouro());
                        intent.putExtra("localidade", endereco.getLocalidade());
                        intent.putExtra("uf", endereco.getUf());
                        intent.putExtra("bairro", endereco.getBairro());
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(BuscaActivity.this, "Cep não encontrado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(BuscaActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    public void navigate(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}