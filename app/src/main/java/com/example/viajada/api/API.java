package com.example.viajada.api;

import com.example.viajada.api.ViagemEndPoint.ViagemEndPoint;
import com.example.viajada.api.ViagemEndPoint.ViagemCustoGasolinaEndPoint;
import com.example.viajada.api.ViagemEndPoint.ViagemCustoAereoEndPoint;
import com.example.viajada.api.ViagemEndPoint.ViagemCustoRefeicaoEndPoint;
import com.example.viajada.api.ViagemEndPoint.ViagemCustoHospedagemEndPoint;
import com.example.viajada.api.ViagemEndPoint.ViagemCustoEntretenimentoEndPoint;
import com.example.viajada.api.response.TB_VIAGEM;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_GASOLINA;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_AEREO;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_REFEICAO;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_HOSPEDAGEM;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_ENTRETENIMENTO;
import com.example.viajada.api.response.Resposta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String URL_ROOT = "http://api.genialsaude.com.br/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void postViagem(TB_VIAGEM viagem, Callback<Resposta> callback) {
        ViagemEndPoint endpoint = retrofit.create(ViagemEndPoint.class);
        Call<Resposta> call = endpoint.postViagem(viagem);
        call.enqueue(callback);
    }

    public static void postViagemCustoGasolina(TB_VIAGEM_CUSTO_GASOLINA viagemCustoGasolina, Callback<Resposta> callback) {
        ViagemCustoGasolinaEndPoint endpoint = retrofit.create(ViagemCustoGasolinaEndPoint.class);
        Call<Resposta> call = endpoint.postViagemCustoGasolina(viagemCustoGasolina);
        call.enqueue(callback);
    }

    public static void postViagemCustoAereo(TB_VIAGEM_CUSTO_AEREO viagemCustoAereo, Callback<Resposta> callback) {
        ViagemCustoAereoEndPoint endpoint = retrofit.create(ViagemCustoAereoEndPoint.class);
        Call<Resposta> call = endpoint.postViagemCustoAereo(viagemCustoAereo);
        call.enqueue(callback);
    }

    public static void postViagemCustoRefeicao(TB_VIAGEM_CUSTO_REFEICAO viagemCustoRefeicao, Callback<Resposta> callback) {
        ViagemCustoRefeicaoEndPoint endpoint = retrofit.create(ViagemCustoRefeicaoEndPoint.class);
        Call<Resposta> call = endpoint.postViagemCustoRefeicao(viagemCustoRefeicao);
        call.enqueue(callback);
    }

    public static void postViagemCustoHospedagem(TB_VIAGEM_CUSTO_HOSPEDAGEM viagemCustoHospedagem, Callback<Resposta> callback) {
        ViagemCustoHospedagemEndPoint endpoint = retrofit.create(ViagemCustoHospedagemEndPoint.class);
        Call<Resposta> call = endpoint.postViagemCustoHospedagem(viagemCustoHospedagem);
        call.enqueue(callback);
    }

    public static void postViagemCustoEntretenimento(TB_VIAGEM_CUSTO_ENTRETENIMENTO viagemCustoEntretenimento, Callback<Resposta> callback) {
        ViagemCustoEntretenimentoEndPoint endpoint = retrofit.create(ViagemCustoEntretenimentoEndPoint.class);
        Call<Resposta> call = endpoint.postViagemCustoEntretenimento(viagemCustoEntretenimento);
        call.enqueue(callback);
    }

    // MÃ©todos GET

    public static void getViagem(Long viagemId, Callback<ArrayList<TB_VIAGEM>> callback) {
        ViagemEndPoint endpoint = retrofit.create(ViagemEndPoint.class);
        Call<ArrayList<TB_VIAGEM>> call = endpoint.getViagem(viagemId);
        call.enqueue(callback);
    }

    public static void getViagemConta(Long contaId, Callback<ArrayList<TB_VIAGEM>> callback) {
        ViagemEndPoint endpoint = retrofit.create(ViagemEndPoint.class);
        Call<ArrayList<TB_VIAGEM>> call = endpoint.getViagemConta(contaId);
        call.enqueue(callback);
    }
}