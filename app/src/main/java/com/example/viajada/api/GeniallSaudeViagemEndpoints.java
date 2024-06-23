package com.example.viajada.api;
import com.example.viajada.api.dto.GenialSaudeViagem;
import com.example.viajada.api.dto.GenialSaudeResponse;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeniallSaudeViagemEndpoints {
    @POST("api/cadastro/viagem")
    Call<GenialSaudeResponse> postViagem(@Body GenialSaudeViagem viagem);

    @GET("api/listar/viagem")
    Call<ArrayList<GenialSaudeViagem>> getViagem(@Query("viagemId") Long viagemId);

    @GET("api/listar/viagem/conta")
    Call<ArrayList<GenialSaudeViagem>> getViagemConta(@Query("contaId") Long contaId);
}
