package com.example.viajada.api.ViagemEndPoint;
import com.example.viajada.api.response.TB_VIAGEM;
import com.example.viajada.api.response.Resposta;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ViagemEndPoint {
    @POST("api/cadastro/viagem")
    Call<Resposta> postViagem(@Body TB_VIAGEM viagem);

    @GET("api/listar/viagem")
    Call<ArrayList<TB_VIAGEM>> getViagem(@Query("viagemId") Long viagemId);

    @GET("api/listar/viagem/conta")
    Call<ArrayList<TB_VIAGEM>> getViagemConta(@Query("contaId") Long contaId);
}
