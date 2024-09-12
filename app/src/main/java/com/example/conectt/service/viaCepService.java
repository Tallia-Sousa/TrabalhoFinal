package com.example.conectt.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


//definição dos metodos que serão utilizados
public interface viaCepService {
    @GET("{cep}/json/")
    Call<Endereco> getEndereco(@Path("cep") String cep);
}
