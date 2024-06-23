package com.example.viajada.api.dto;

import java.io.Serializable;

public class GenialSaudeResponse implements Serializable {

    private boolean Sucesso;
    private String Mensagem;
    private String ChavePrimaria;

    public boolean isSucesso() {
        return Sucesso;
    }

    public void setSucesso(boolean sucesso) {
        Sucesso = sucesso;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public String getChavePrimaria() {
        return ChavePrimaria;
    }

    public void setChavePrimaria(String chavePrimaria) {
        ChavePrimaria = chavePrimaria;
    }
}
