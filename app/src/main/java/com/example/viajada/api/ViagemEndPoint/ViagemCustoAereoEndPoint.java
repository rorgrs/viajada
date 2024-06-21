package com.example.viajada.api.ViagemEndPoint;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_AEREO;
import com.example.viajada.api.response.Resposta;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ViagemCustoAereoEndPoint {
    @POST("api/cadastro/viagem_custo_aereo")
    Call<Resposta> postViagemCustoAereo(@Body TB_VIAGEM_CUSTO_AEREO viagemCustoAereo);

    @GET("api/listar/viagem_custo_aereo")
    Call<ArrayList<TB_VIAGEM_CUSTO_AEREO>> getViagemCustoAereo(@Query("viagemId") Long viagemId);
}
