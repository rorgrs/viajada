package com.example.viajada.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.viajada.database.model.UsuarioModel;
import com.example.viajada.database.model.ViagemModel;

public class SharedHelper {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String UsuarioId = "usuario" + UsuarioModel.COLUNA_ID;
    public static final String ViagemId = "viagem" + ViagemModel.COLUNA_ID;
    public static final String ViagemPrincipalOrigem = "viagem_" + ViagemModel.COLUNA_PRINCIPAL_ORIGEM;
    public static final String ViagemPrincipalDestino = "viagem_" + ViagemModel.COLUNA_PRINCIPAL_DESTINO;
    public static final String ViagemPrincipalDuracaoDias = "viagem_" + ViagemModel.COLUNA_PRINCIPAL_DURACAO_DIAS;
    public static final String ViagemPrincipalNumViajantes = "viagem_" + ViagemModel.COLUNA_PRINCIPAL_NUM_VIAJANTES;
    public static final String ViagemCombustivelDistanciaKm = "viagem_" + ViagemModel.COLUNA_COMBUSTIVEL_DISTANCIA_TOTAL_KM;
    public static final String ViagemCombustivelKmLitro = "viagem_" + ViagemModel.COLUNA_COMBUSTIVEL_MEDIA_KM_LITRO;
    public static final String ViagemCombustivelCustoLitro = "viagem_" + ViagemModel.COLUNA_COMBUSTIVEL_CUSTO_MEDIO_LITRO;
    public static final String ViagemCombustivelNumVeiculos = "viagem_" + ViagemModel.COLUNA_COMBUSTIVEL_NUM_VEICULOS;
    public static final String ViagemTarifaAereaCustoPorPessoa = "viagem_" + ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_PESSOA;
    public static final String ViagemTarifaAereaCustoAluguelVeiculo = "viagem_" + ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_ALUGUEL_VEICULO;
    public static final String ViagemRefeicaoCustoMedio = "viagem_" + ViagemModel.COLUNA_REFEICAO_CUSTO_MEDIO;
    public static final String ViagemRefeicaoNumPorDia = "viagem_" + ViagemModel.COLUNA_REFEICAO_POR_DIA;
    public static final String ViagemHospedagemCustoNoite = "viagem_" + ViagemModel.COLUNA_HOSPEDAGEM_CUSTO_MEDIO_NOITE;
    public static final String ViagemHospedagemNumNoites = "viagem_" + ViagemModel.COLUNA_HOSPEDAGEM_TOTAL_NOITES;
    public static final String ViagemValorTotal = "viagem_valor_total";

    public SharedHelper(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }

    public String GetString(String campo){
        return this.prefs.getString(campo, null);
    }

    public int GetInt(String campo){
        return this.prefs.getInt(campo, 0);
    }

    public long GetLong(String campo){
        return this.prefs.getLong(campo, 0);
    }

    public boolean GetBoolean(String campo){
        return this.prefs.getBoolean(campo, false);
    }

    

    public void SetString(String campo, String valor){
        this.editor.putString(campo, null);
        this.editor.apply();
    }

    public void SetInt(String campo, int valor){
        this.editor.putInt(campo, valor);
        this.editor.apply();
    }

    public void SetLong(String campo, long valor){
        this.editor.putLong(campo, valor);
        this.editor.apply();
    }

    public void SetBoolean(String campo, boolean valor){
        this.editor.putBoolean(campo, valor);
        this.editor.apply();
    }

    public void Clear(){
        this.editor.clear();
        this.editor.commit();
    }
}
