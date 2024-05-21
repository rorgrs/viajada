package com.example.viajada.database.model;

import java.util.List;

public class ViagemModel {

    public static String TABELA_NOME = "tb_viagem";

    public static String
            COLUNA_ID = "_id",
            COLUNA_USUARIO_ID = "usuario_id",
            COLUNA_PRINCIPAL_ORIGEM = "principal_origem",
            COLUNA_PRINCIPAL_DESTINO = "principal_destino",
            COLUNA_PRINCIPAL_DURACAO_DIAS = "duracao_dias",
            COLUNA_PRINCIPAL_NUM_VIAJANTES = "principal_num_viajantes",
            COLUNA_COMBUSTIVEL_DISTANCIA_TOTAL_KM = "combustivel_distancia_total_km",
            COLUNA_COMBUSTIVEL_MEDIA_KM_LITRO = "combustivel_media_km_litro",
            COLUNA_COMBUSTIVEL_CUSTO_MEDIO_LITRO = "combustivel_custo_medio_litro",
            COLUNA_COMBUSTIVEL_NUM_VEICULOS = "combustivel_num_veiculos",
            COLUNA_TARIFA_AEREA_CUSTO_PESSOA = "tarifa_aerea_custo_pessoa",
            COLUNA_TARIFA_AEREA_CUSTO_ALUGUEL_VEICULO = "tarifa_aerea_custo_aluguel_veiculo",
            COLUNA_REFEICAO_CUSTO_MEDIO = "refeicao_custo_medio",
            COLUNA_REFEICAO_POR_DIA = "refeicao_por_dia",
            COLUNA_HOSPEDAGEM_CUSTO_MEDIO_NOITE = "hospedagem_custo_medio_noite",
            COLUNA_HOSPEDAGEM_TOTAL_NOITES = "hospedagem_total_noites",
            COLUNA_HOSPEDAGEM_TOTAL_QUARTOS = "hospedagem_total_quartos";

    public static String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_PRINCIPAL_ORIGEM + " TEXT NOT NULL, "
                    + COLUNA_PRINCIPAL_DESTINO + " TEXT NOT NULL, "
                    + COLUNA_PRINCIPAL_DURACAO_DIAS + " INTEGER NOT NULL, "
                    + COLUNA_PRINCIPAL_NUM_VIAJANTES + " INTEGER NOT NULL, "
                    + COLUNA_COMBUSTIVEL_DISTANCIA_TOTAL_KM + " INTEGER NOT NULL, "
                    + COLUNA_COMBUSTIVEL_MEDIA_KM_LITRO + " REAL NOT NULL, "
                    + COLUNA_COMBUSTIVEL_CUSTO_MEDIO_LITRO + " REAL NOT NULL, "
                    + COLUNA_COMBUSTIVEL_NUM_VEICULOS + " INTEGER NOT NULL, "
                    + COLUNA_TARIFA_AEREA_CUSTO_PESSOA + " REAL, "
                    + COLUNA_TARIFA_AEREA_CUSTO_ALUGUEL_VEICULO + " REAL, "
                    + COLUNA_REFEICAO_CUSTO_MEDIO + " REAL NOT NULL, "
                    + COLUNA_REFEICAO_POR_DIA + " INTEGER NOT NULL, "
                    + COLUNA_HOSPEDAGEM_CUSTO_MEDIO_NOITE + " REAL, "
                    + COLUNA_HOSPEDAGEM_TOTAL_NOITES + " INTEGER, "
                    + COLUNA_HOSPEDAGEM_TOTAL_QUARTOS + " INTEGER "
                    + " );";

    public static String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private long usuario_id;
    private String principalOrigem;
    private String principalDestino;
    private int principalDuracaoDias;
    private int principalNumViajantes;
    private int combustivelDistanciaTotalKm;
    private float combustivelMediaKmLitro;
    private float combustivelCustoMedioLitro;
    private int combustivelNumVeiculos;
    private Float tarifaAereaCustoPessoa;
    private Float tarifaAereaCustoAluguelVeiculo;
    private float refeicaoCustoMedio;
    private int refeicaoPorDia;
    private Float hospedagemCustoMedioNoite;
    private Integer hospedagemTotalNoites;
    private Integer hospedagemTotalQuartos;
    private List<ViagemCustoAdicionalModel> custosAdicionais;

    public float getCustoTotal() {
        return getCustoCombustivel() + getCustoHospedagem() + getCustoGastosAdicionais() + getCustoTarifaAerea() + getCustoRefeicoes();
    }

    public float getCustoCombustivel() {
        return (((float) combustivelDistanciaTotalKm / combustivelMediaKmLitro) * combustivelCustoMedioLitro) / combustivelNumVeiculos;
    }

    public float getCustoHospedagem() {
        return hospedagemCustoMedioNoite * hospedagemTotalQuartos * hospedagemTotalNoites;
    }

    public float getCustoTarifaAerea() {
        if (tarifaAereaCustoPessoa == null || tarifaAereaCustoAluguelVeiculo == null) return 0;
        return (principalNumViajantes * tarifaAereaCustoPessoa) + tarifaAereaCustoAluguelVeiculo;
    }

    public float getCustoRefeicoes() {
        return principalNumViajantes * refeicaoPorDia * refeicaoCustoMedio * principalDuracaoDias;
    }

    public float getCustoGastosAdicionais() {
        if (custosAdicionais == null || custosAdicionais.isEmpty()) return 0;

        float soma = 0;

        for (ViagemCustoAdicionalModel custo : custosAdicionais) {
            soma += custo.getCusto();
        }

        return soma;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuario_id = id;
    }

    public String getPrincipalOrigem() {
        return principalOrigem;
    }

    public void setPrincipalOrigem(String principalOrigem) {
        this.principalOrigem = principalOrigem;
    }

    public String getPrincipalDestino() {
        return principalDestino;
    }

    public void setPrincipalDestino(String principalDestino) {
        this.principalDestino = principalDestino;
    }

    public int getPrincipalDuracaoDias() {
        return principalDuracaoDias;
    }

    public void setPrincipalDuracaoDias(int principalDuracaoDias) {
        this.principalDuracaoDias = principalDuracaoDias;
    }

    public int getPrincipalNumViajantes() {
        return principalNumViajantes;
    }

    public void setPrincipalNumViajantes(int principalNumViajantes) {
        this.principalNumViajantes = principalNumViajantes;
    }

    public int getCombustivelDistanciaTotalKm() {
        return combustivelDistanciaTotalKm;
    }

    public void setCombustivelDistanciaTotalKm(int combustivelDistanciaTotalKm) {
        this.combustivelDistanciaTotalKm = combustivelDistanciaTotalKm;
    }

    public float getCombustivelMediaKmLitro() {
        return combustivelMediaKmLitro;
    }

    public void setCombustivelMediaKmLitro(float combustivelMediaKmLitro) {
        this.combustivelMediaKmLitro = combustivelMediaKmLitro;
    }

    public float getCombustivelCustoMedioLitro() {
        return combustivelCustoMedioLitro;
    }

    public void setCombustivelCustoMedioLitro(float combustivelCustoMedioLitro) {
        this.combustivelCustoMedioLitro = combustivelCustoMedioLitro;
    }

    public int getCombustivelNumVeiculos() {
        return combustivelNumVeiculos;
    }

    public void setCombustivelNumVeiculos(int combustivelNumVeiculos) {
        this.combustivelNumVeiculos = combustivelNumVeiculos;
    }

    public Float getTarifaAereaCustoPessoa() {
        return tarifaAereaCustoPessoa;
    }

    public void setTarifaAereaCustoPessoa(Float tarifaAereaCustoPessoa) {
        this.tarifaAereaCustoPessoa = tarifaAereaCustoPessoa;
    }

    public Float getTarifaAereaCustoAluguelVeiculo() {
        return tarifaAereaCustoAluguelVeiculo;
    }

    public void setTarifaAereaCustoAluguelVeiculo(Float tarifaAereaCustoAluguelVeiculo) {
        this.tarifaAereaCustoAluguelVeiculo = tarifaAereaCustoAluguelVeiculo;
    }

    public float getRefeicaoCustoMedio() {
        return refeicaoCustoMedio;
    }

    public void setRefeicaoCustoMedio(float refeicaoCustoMedio) {
        this.refeicaoCustoMedio = refeicaoCustoMedio;
    }

    public int getRefeicaoPorDia() {
        return refeicaoPorDia;
    }

    public void setRefeicaoPorDia(int refeicaoPorDia) {
        this.refeicaoPorDia = refeicaoPorDia;
    }

    public Float getHospedagemCustoMedioNoite() {
        return hospedagemCustoMedioNoite;
    }

    public void setHospedagemCustoMedioNoite(Float hospedagemCustoMedioNoite) {
        this.hospedagemCustoMedioNoite = hospedagemCustoMedioNoite;
    }

    public Integer getHospedagemTotalNoites() {
        return hospedagemTotalNoites;
    }

    public void setHospedagemTotalNoites(Integer hospedagemTotalNoites) {
        this.hospedagemTotalNoites = hospedagemTotalNoites;
    }

    public Integer getHospedagemTotalQuartos() {
        return hospedagemTotalQuartos;
    }

    public void setHospedagemTotalQuartos(Integer hospedagemTotalQuartos) {
        this.hospedagemTotalQuartos = hospedagemTotalQuartos;
    }


    public List<ViagemCustoAdicionalModel> getCustosAdicionais() {
        return custosAdicionais;
    }

    public void setCustosAdicionais(List<ViagemCustoAdicionalModel> custosAdicionais) {
        this.custosAdicionais = custosAdicionais;
    }
}
