package com.example.viajada.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.viajada.database.DBOpenHelper;
import com.example.viajada.database.model.ViagemCustoAdicionalModel;
import com.example.viajada.database.model.ViagemModel;

import java.util.ArrayList;
import java.util.List;

public class ViagemCustoAdicionalDao extends BaseDao {

    private String[] colunas = {ViagemCustoAdicionalModel.COLUNA_ID, ViagemCustoAdicionalModel.COLUNA_VIAGEM_ID, ViagemCustoAdicionalModel.COLUNA_DESCRICAO, ViagemCustoAdicionalModel.COLUNA_CUSTO};

    public ViagemCustoAdicionalDao(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public void Inserir(List<ViagemCustoAdicionalModel> lista, long viagemId) {

        try {
            Open();

            for (ViagemCustoAdicionalModel model : lista) {
                ContentValues values = new ContentValues();

                values.put(ViagemCustoAdicionalModel.COLUNA_VIAGEM_ID, viagemId);
                values.put(ViagemCustoAdicionalModel.COLUNA_DESCRICAO, model.getDescricao());
                values.put(ViagemCustoAdicionalModel.COLUNA_CUSTO, model.getCusto());

                db.insert(ViagemModel.TABELA_NOME, null, values);
            }

        } finally {
            Close();
        }
    }

    public void DeletarPorViagemId(long viagemId) {
        try {
            Open();

            // delete from tb_viagem_custo_adicional where viagem_id = 12345;
            db.delete(ViagemCustoAdicionalModel.TABELA_NOME, ViagemCustoAdicionalModel.COLUNA_VIAGEM_ID + " = ?", new String[]{String.valueOf(viagemId)});
        } finally {
            Close();
        }
    }

    public ArrayList<ViagemCustoAdicionalModel> ConsultarPorViagemId(long viagemId) {

        Open();

        ArrayList<ViagemCustoAdicionalModel> lista = new ArrayList<ViagemCustoAdicionalModel>();

        // select * from tb_produtos;
        Cursor c = db.query(ViagemCustoAdicionalModel.TABELA_NOME, colunas, ViagemCustoAdicionalModel.COLUNA_VIAGEM_ID + " = ?", new String[]{String.valueOf(viagemId)}, null, null, null);

        boolean existe = c.moveToFirst();

        if (!existe) return lista;

        while (!c.isAfterLast()) {
            ViagemCustoAdicionalModel model = new ViagemCustoAdicionalModel();

            model.setId(c.getInt(0));

            model.setViagemId(c.getInt(1));
            model.setDescricao(c.getString(2));
            model.setCusto(c.getFloat(3));

            lista.add(model);

            c.moveToNext();
        }

        c.close();

        Close();

        return lista;
    }
}
