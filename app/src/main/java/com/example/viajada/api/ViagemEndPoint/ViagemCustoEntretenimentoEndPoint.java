package com.example.viajada.api.ViagemEndPoint;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_ENTRETENIMENTO;
import com.example.viajada.api.response.Resposta;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ViagemCustoEntretenimentoEndPoint {
    @POST("api/cadastro/viagem_custo_entretenimento")
    Call<Resposta> postViagemCustoEntretenimento(@Body TB_VIAGEM_CUSTO_ENTRETENIMENTO viagemCustoEntretenimento);

    @GET("api/listar/viagem_custo_entretenimento")
    Call<ArrayList<TB_VIAGEM_CUSTO_ENTRETENIMENTO>> getViagemCustoEntretenimento(@Query("viagemId") Long viagemId);
}
