package com.example.viajada.api.response;

public class TB_VIAGEM_CUSTO_REFEICAO {
    private Long id;
    private Long viagemId;
    private double custoRefeicao;
    private int refeicoesDia;

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

    public double getCustoRefeicao() {
        return custoRefeicao;
    }

    public void setCustoRefeicao(double custoRefeicao) {
        this.custoRefeicao = custoRefeicao;
    }

    public int getRefeicoesDia() {
        return refeicoesDia;
    }

    public void setRefeicoesDia(int refeicoesDia) {
        this.refeicoesDia = refeicoesDia;
    }
}
