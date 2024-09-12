package com.example.conectt.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //configurar pra obter uma instancia do retrofit
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    //url base que define todas as requisiçoes que vao ser feitas como
                    // prefixo para todos os endpoints da API.
                    .baseUrl(baseUrl)
                    //converte o json para objeto java
                    .addConverterFactory(GsonConverterFactory.create())
                    //constroi e retorna a instancia do retrofit consigurada
                    .build();
        }
        return retrofit;
    }
}
