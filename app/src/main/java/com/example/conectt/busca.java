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

public class busca extends AppCompatActivity {

    private EditText cepInput;
    private Button buscarButton;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isHorizontal = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca);

        cepInput = findViewById(R.id.cepInput);
        buscarButton = findViewById(R.id.buscarButton);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometerSensor == null) {
            Toast.makeText(this, "O dispositivo não tem o sensor necessário.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        buscarButton.setOnClickListener(v -> {
            String cep = cepInput.getText().toString().trim();
            if (!cep.isEmpty()) {
                buscarCep(cep);
            } else {
                Toast.makeText(busca.this, "Por favor, insira um CEP", Toast.LENGTH_LONG).show();
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
                        Intent intent = new Intent(busca.this, result.class);
                        intent.putExtra("logradouro", endereco.getLogradouro());
                        intent.putExtra("localidade", endereco.getLocalidade());
                        intent.putExtra("uf", endereco.getUf());
                        startActivity(intent);
                    } else {
                        Toast.makeText(busca.this, "Endereço não encontrado", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(busca.this, "Falha na resposta da API", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(busca.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(accelerometerListener);
    }

    private final SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];

            if (!isHorizontal && Math.abs(x) > 5 && Math.abs(y) < 5) {
                isHorizontal = true;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else if (isHorizontal && Math.abs(x) < 5 && Math.abs(y) > 5) {
                isHorizontal = false;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
