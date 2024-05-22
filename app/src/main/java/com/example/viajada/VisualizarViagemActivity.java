package com.example.viajada;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viajada.database.dao.ViagemDao;
import com.example.viajada.database.model.ViagemModel;

public class VisualizarViagemActivity extends AppCompatActivity {

    private TextView origem, destino, duracao, numeroViajantes, totalKm, mediaLitro, custoLitro,
            totalVeiculos, custoPessoa, aluguelVeiculo, custoRefeicao, refeicaoDia, custoNoite,
            totalNoites, totalQuartos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_viagem);

        origem = findViewById(R.id.origem);
        destino = findViewById(R.id.destino);
        duracao = findViewById(R.id.duracao);
        numeroViajantes = findViewById(R.id.numero_viagens);
        totalKm = findViewById(R.id.total_km);
        mediaLitro = findViewById(R.id.media_litro);
        custoLitro = findViewById(R.id.custo_litro);
        totalVeiculos = findViewById(R.id.total_veiculos);
        custoPessoa = findViewById(R.id.custo_pessoa);
        aluguelVeiculo = findViewById(R.id.aluguel_veiculo);
        custoRefeicao = findViewById(R.id.custo_refeicao);
        refeicaoDia = findViewById(R.id.refeicao_dia);
        custoNoite = findViewById(R.id.custo_noite);
        totalNoites = findViewById(R.id.total_noites);
        totalQuartos = findViewById(R.id.total_quartos);

        long viagemId = getIntent().getLongExtra("VIAGEM_ID", -1);
        if (viagemId != -1) {
            carregarDadosViagem(viagemId);
        }
    }

    private void carregarDadosViagem(long viagemId) {
        ViagemDao viagemDao = new ViagemDao(this);
        ViagemModel viagem = viagemDao.ConsultarPorId(viagemId);

        if (viagem != null) {
            origem.setText("Origem: " + viagem.getPrincipalOrigem());
            destino.setText("Destino: " + viagem.getPrincipalDestino());
            duracao.setText("Duração: " + viagem.getPrincipalDuracaoDias() + " dias");
            numeroViajantes.setText("Número de Viajantes: " + viagem.getPrincipalNumViajantes());
            totalKm.setText("Total Estimado em KM: " + viagem.getCombustivelDistanciaTotalKm());
            mediaLitro.setText("Média por Litro: " + viagem.getCombustivelMediaKmLitro());
            custoLitro.setText("Custo por Litro: " + viagem.getCombustivelCustoMedioLitro());
            totalVeiculos.setText("Total de Veículos: " + viagem.getCombustivelNumVeiculos());
            custoPessoa.setText("Custo por Pessoa: " + viagem.getTarifaAereaCustoPessoa());
            aluguelVeiculo.setText("Aluguel do Veículo: " + viagem.getTarifaAereaCustoAluguelVeiculo());
            custoRefeicao.setText("Custo por Refeição: " + viagem.getRefeicaoCustoMedio());
            refeicaoDia.setText("Número de Refeições por Dia: " + viagem.getRefeicaoPorDia());
            custoNoite.setText("Custo Médio por Noite: " + viagem.getHospedagemCustoMedioNoite());
            totalNoites.setText("Total de Noites: " + viagem.getHospedagemTotalNoites());
            totalQuartos.setText("Total de Quartos: " + viagem.getHospedagemTotalQuartos());
        }
    }
}
