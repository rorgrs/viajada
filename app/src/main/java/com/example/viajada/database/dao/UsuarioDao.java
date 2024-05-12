package com.example.viajada.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.viajada.database.DBOpenHelper;
import com.example.viajada.database.model.UsuarioModel;
import com.example.viajada.database.model.ViagemCustoAdicionalModel;
import com.example.viajada.database.model.ViagemModel;

import java.util.ArrayList;

public class UsuarioDao extends BaseDao {

    private String[] colunas = {
            UsuarioModel.COLUNA_ID,
            UsuarioModel.COLUNA_LOGIN,
            UsuarioModel.COLUNA_NOME,
            UsuarioModel.COLUNA_SENHA
    };

    public UsuarioDao(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long Inserir(UsuarioModel model) {

        long rowId = -1;

        try {
            Open();

            //colunas obrigatorias
            ContentValues values = new ContentValues();
            values.put(UsuarioModel.COLUNA_LOGIN, model.getLogin());
            values.put(UsuarioModel.COLUNA_NOME, model.getNome());
            values.put(UsuarioModel.COLUNA_SENHA, model.getSenha());

            rowId = db.insert(UsuarioModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }

        return rowId;
    }

    public UsuarioModel ConsultarPorId(long id) {
        Open();

        // select * from tb_produtos;
        Cursor c = db.query
                (
                        UsuarioModel.TABELA_NOME,
                        colunas,
                        UsuarioModel.COLUNA_ID + " = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null
                );

        c.moveToFirst();

        UsuarioModel model = new UsuarioModel();

        model.setId(c.getInt(0));

        model.setLogin(c.getString(1));
        model.setNome(c.getString(2));
        model.setSenha(c.getString(3));

        Close();

        return model;
    }

}
