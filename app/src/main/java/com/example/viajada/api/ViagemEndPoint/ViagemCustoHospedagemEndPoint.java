package com.example.viajada.api.ViagemEndPoint;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_HOSPEDAGEM;
import com.example.viajada.api.response.Resposta;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ViagemCustoHospedagemEndPoint {
    @POST("api/cadastro/viagem_custo_hospedagem")
    Call<Resposta> postViagemCustoHospedagem(@Body TB_VIAGEM_CUSTO_HOSPEDAGEM viagemCustoHospedagem);

    @GET("api/listar/viagem_custo_hospedagem")
    Call<ArrayList<TB_VIAGEM_CUSTO_HOSPEDAGEM>> getViagemCustoHospedagem(@Query("viagemId") Long viagemId);
}
