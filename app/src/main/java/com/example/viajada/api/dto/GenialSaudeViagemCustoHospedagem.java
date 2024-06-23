package com.example.viajada.api.dto;

public class GenialSaudeViagemCustoHospedagem {
    private Long viagemId;
    private Long idConta = 7L;
    private double custoMedioNoite;
    private int totalNoite;
    private int totalQuartos;

    public Long getViagemId() {
        return viagemId;
    }

    public void setViagemId(Long viagemId) {
        this.viagemId = viagemId;
    }

    public Long getIdConta() {
        return idConta;
    }

    public void setIdConta(Long idConta) {
        this.idConta = idConta;
    }

    public double getCustoMedioNoite() {
        return custoMedioNoite;
    }

    public void setCustoMedioNoite(double custoMedioNoite) {
        this.custoMedioNoite = custoMedioNoite;
    }

    public int getTotalNoite() {
        return totalNoite;
    }

    public void setTotalNoite(int totalNoite) {
        this.totalNoite = totalNoite;
    }

    public int getTotalQuartos() {
        return totalQuartos;
    }

    public void setTotalQuartos(int totalQuartos) {
        this.totalQuartos = totalQuartos;
    }
}
