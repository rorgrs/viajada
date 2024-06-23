package com.example.viajada.api.dto;

public class GenialSaudeViagem {
    private Long id;
    private Long idConta = 7L;
    private int totalViajantes;
    private int duracaoViagem;
    private double custoTotalViagem;
    private double custoPorPessoa;
    private String local;
    private GenialSaudeViagemCustoRefeicao refeicao;
    private GenialSaudeViagemCustoHospedagem hospedagem;
    private GenialSaudeViagemCustoGasolina gasolina;
    private GenialSaudeViagemCustoAereo aereo;
    private GenialSaudeViagemCustoEntretenimento[] listaEntretenimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdConta() {
        return idConta;
    }

    public void setIdConta(Long idConta) {
        this.idConta = idConta;
    }

    public int getTotalViajantes() {
        return totalViajantes;
    }

    public void setTotalViajantes(int totalViajantes) {
        this.totalViajantes = totalViajantes;
    }

    public int getDuracaoViagem() {
        return duracaoViagem;
    }

    public void setDuracaoViagem(int duracaoViagem) {
        this.duracaoViagem = duracaoViagem;
    }

    public double getCustoTotalViagem() {
        return custoTotalViagem;
    }

    public void setCustoTotalViagem(double custoTotalViagem) {
        this.custoTotalViagem = custoTotalViagem;
    }

    public double getCustoPorPessoa() {
        return custoPorPessoa;
    }

    public void setCustoPorPessoa(double custoPorPessoa) {
        this.custoPorPessoa = custoPorPessoa;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public GenialSaudeViagemCustoRefeicao getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(GenialSaudeViagemCustoRefeicao refeicao) {
        this.refeicao = refeicao;
    }

    public GenialSaudeViagemCustoHospedagem getHospedagem() {
        return hospedagem;
    }

    public void setHospedagem(GenialSaudeViagemCustoHospedagem hospedagem) {
        this.hospedagem = hospedagem;
    }

    public GenialSaudeViagemCustoGasolina getGasolina() {
        return gasolina;
    }

    public void setGasolina(GenialSaudeViagemCustoGasolina gasolina) {
        this.gasolina = gasolina;
    }

    public GenialSaudeViagemCustoAereo getAereo() {
        return aereo;
    }

    public void setAereo(GenialSaudeViagemCustoAereo aereo) {
        this.aereo = aereo;
    }

    public GenialSaudeViagemCustoEntretenimento[] getListaEntretenimento() {
        return listaEntretenimento;
    }

    public void setListaEntretenimento(GenialSaudeViagemCustoEntretenimento[] listaEntretenimento) {
        this.listaEntretenimento = listaEntretenimento;
    }
}
