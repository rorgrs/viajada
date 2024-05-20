package com.example.viajada.database.model;

public class ViagemCustoAdicionalModel {

    public static String TABELA_NOME = "tb_viagem_custo_adicional";

    public static String
            COLUNA_ID = "_id",
            COLUNA_VIAGEM_ID = "viagem_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_CUSTO = "custo";

    public static String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_VIAGEM_ID + " INTEGER NOT NULL REFERENCES "+ViagemModel.TABELA_NOME+", "
                    + COLUNA_DESCRICAO + " TEXT NOT NULL, "
                    + COLUNA_CUSTO + " REAL NOT NULL "
                    + " );";

    public static String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private int viagemId;
    private String descricao;
    private float custo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getViagemId() {
        return viagemId;
    }

    public void setViagemId(int viagemId) {
        this.viagemId = viagemId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }
}
