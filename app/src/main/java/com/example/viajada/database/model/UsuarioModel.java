package com.example.viajada.database.model;

import java.util.List;

public class UsuarioModel {

    public static String TABELA_NOME = "tb_usuario";

    public static String
            COLUNA_ID = "_id",
            COLUNA_LOGIN = "login",
            COLUNA_NOME = "nome",
            COLUNA_SENHA = "senha";

    public static String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_LOGIN + " TEXT NOT NULL, "
                    + COLUNA_NOME + " TEXT, "
                    + COLUNA_SENHA + " TEXT NOT NULL "
                    + " );";

    public static String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private String login;
    private String nome;
    private String senha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
