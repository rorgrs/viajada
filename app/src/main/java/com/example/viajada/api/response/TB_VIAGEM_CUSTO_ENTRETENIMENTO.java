package com.example.viajada.api.response;

public class TB_VIAGEM_CUSTO_ENTRETENIMENTO {
    private Long id;
    private Long viagemId;
    private String entretenimento;
    private double valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViagemId() {
        return viagemId;
    }

    public void setViagemId(Long viagemId) {
        this.viagemId = viagemId;
    }

    public String getEntretenimento() {
        return entretenimento;
    }

    public void setEntretenimento(String entretenimento) {
        this.entretenimento = entretenimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
