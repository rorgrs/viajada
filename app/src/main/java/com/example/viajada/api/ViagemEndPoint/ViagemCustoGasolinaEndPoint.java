package com.example.viajada.api.ViagemEndPoint;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_GASOLINA;
import com.example.viajada.api.response.Resposta;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ViagemCustoGasolinaEndPoint {
    @POST("api/cadastro/viagem_custo_gasolina")
    Call<Resposta> postViagemCustoGasolina(@Body TB_VIAGEM_CUSTO_GASOLINA viagemCustoGasolina);

    @GET("api/listar/viagem_custo_gasolina")
    Call<ArrayList<TB_VIAGEM_CUSTO_GASOLINA>> getViagemCustoGasolina(@Query("viagemId") Long viagemId);
}
