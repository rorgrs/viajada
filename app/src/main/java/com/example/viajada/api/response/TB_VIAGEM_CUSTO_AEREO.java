package com.example.viajada.api.response;

public class TB_VIAGEM_CUSTO_AEREO {
    private Long viagemId;
    private double custoPessoa;
    private double custoAluguelVeiculo;

    public Long getViagemId() {
        return viagemId;
    }

    public void setViagemId(Long viagemId) {
        this.viagemId = viagemId;
    }

    public double getCustoPessoa() {
        return custoPessoa;
    }

    public void setCustoPessoa(double custoPessoa) {
        this.custoPessoa = custoPessoa;
    }

    public double getCustoAluguelVeiculo() {
        return custoAluguelVeiculo;
    }

    public void setCustoAluguelVeiculo(double custoAluguelVeiculo) {
        this.custoAluguelVeiculo = custoAluguelVeiculo;
    }
}
