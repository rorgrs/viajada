package com.example.viajada.api;
import com.example.viajada.api.dto.GenialSaudeViagem;
import com.example.viajada.api.dto.GenialSaudeResponse;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenialSaudeApiClient {

    private static final String URL_ROOT = "http://api.genialsaude.com.br/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void postViagem(GenialSaudeViagem viagem, Callback<GenialSaudeResponse> callback) {
        GeniallSaudeViagemEndpoints endpoint = retrofit.create(GeniallSaudeViagemEndpoints.class);
        Call<GenialSaudeResponse> call = endpoint.postViagem(viagem);
        call.enqueue(callback);
    }

    public static void getViagem(Long viagemId, Callback<ArrayList<GenialSaudeViagem>> callback) {
        GeniallSaudeViagemEndpoints endpoint = retrofit.create(GeniallSaudeViagemEndpoints.class);
        Call<ArrayList<GenialSaudeViagem>> call = endpoint.getViagem(viagemId);
        call.enqueue(callback);
    }

    public static void getViagemConta(Long contaId, Callback<ArrayList<GenialSaudeViagem>> callback) {
        GeniallSaudeViagemEndpoints endpoint = retrofit.create(GeniallSaudeViagemEndpoints.class);
        Call<ArrayList<GenialSaudeViagem>> call = endpoint.getViagemConta(contaId);
        call.enqueue(callback);
    }
}