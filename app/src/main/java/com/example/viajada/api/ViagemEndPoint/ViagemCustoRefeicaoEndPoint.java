package com.example.viajada.api.ViagemEndPoint;
import com.example.viajada.api.response.TB_VIAGEM_CUSTO_REFEICAO;
import com.example.viajada.api.response.Resposta;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ViagemCustoRefeicaoEndPoint {
    @POST("api/cadastro/viagem_custo_refeicao")
    Call<Resposta> postViagemCustoRefeicao(@Body TB_VIAGEM_CUSTO_REFEICAO viagemCustoRefeicao);

    @GET("api/listar/viagem_custo_refeicao")
    Call<ArrayList<TB_VIAGEM_CUSTO_REFEICAO>> getViagemCustoRefeicao(@Query("viagemId") Long viagemId);
}
