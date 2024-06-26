package com.example.viajada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.viajada.api.GenialSaudeApiClient;
import com.example.viajada.api.dto.GenialSaudeResponse;
import com.example.viajada.api.dto.GenialSaudeViagem;
import com.example.viajada.api.dto.GenialSaudeViagemCustoAereo;
import com.example.viajada.api.dto.GenialSaudeViagemCustoEntretenimento;
import com.example.viajada.api.dto.GenialSaudeViagemCustoGasolina;
import com.example.viajada.api.dto.GenialSaudeViagemCustoHospedagem;
import com.example.viajada.api.dto.GenialSaudeViagemCustoRefeicao;
import com.example.viajada.database.dao.ViagemCustoAdicionalDao;
import com.example.viajada.database.dao.ViagemDao;
import com.example.viajada.database.model.ViagemCustoAdicionalModel;
import com.example.viajada.database.model.ViagemModel;
import com.example.viajada.helper.AlertHelper;
import com.example.viajada.helper.LayoutHelper;
import com.example.viajada.helper.SharedHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualizarViagemActivity extends AppCompatActivity {

    private SharedHelper sharedHelper;
    private LayoutHelper layoutHelper;
    private ViagemDao viagemDao;
    private ViagemModel viagem;
    private Button btnVoltar, btnEditar, btnEnviar;
    private TextView principalOrigem, principalDestino, principalDuracao, principalNumViajantes, principalValorTotalViagem;
    private TextView combustivelTotalKm, combustivelMediaLitro, combustivelCustoLitro, combustivelTotalVeiculos, combustivelValorTotal;
    private TextView tarifaAereaCustoPessoa, tarifaAereaAluguelVeiculo, tarifaAereaValorTotal;
    private TextView refeicaoCustoPessoa, refeicaoNumPorDia, refeicaoValorTotal;
    private TextView hospedagemCustoNoite, hospedagemNumNoites, hospedagemNumQuartos, hospedagemValorTotal;
    private LinearLayout listagem;
    private AlertHelper alertHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_viagem);

        layoutHelper = new LayoutHelper(VisualizarViagemActivity.this);
        sharedHelper = new SharedHelper(VisualizarViagemActivity.this);
        viagemDao = new ViagemDao(VisualizarViagemActivity.this);
        alertHelper = new AlertHelper(VisualizarViagemActivity.this);

        long viagemId = sharedHelper.GetLong(SharedHelper.ViagemId);

        viagem = viagemDao.ConsultarPorId(viagemId);

        btnVoltar = findViewById(R.id.voltar);
        btnEditar = findViewById(R.id.editar);
        btnEnviar = findViewById(R.id.enviar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voltar();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enviar();
            }
        });

        listagem = (LinearLayout) findViewById(R.id.listagem);

        principalOrigem = findViewById(R.id.origem);
        principalDestino = findViewById(R.id.destino);
        principalDuracao = findViewById(R.id.duracao);
        principalNumViajantes = findViewById(R.id.numero_viagens);
        principalValorTotalViagem = findViewById(R.id.total_viagem);

        combustivelTotalKm = findViewById(R.id.total_km);
        combustivelMediaLitro = findViewById(R.id.media_litro);
        combustivelCustoLitro = findViewById(R.id.custo_litro);
        combustivelTotalVeiculos = findViewById(R.id.total_veiculos);
        combustivelValorTotal = findViewById(R.id.total_combustivel);

        tarifaAereaCustoPessoa = findViewById(R.id.custo_pessoa);
        tarifaAereaAluguelVeiculo = findViewById(R.id.aluguel_veiculo);
        tarifaAereaValorTotal = findViewById(R.id.total_tarifa_aerea);

        refeicaoCustoPessoa = findViewById(R.id.custo_refeicao);
        refeicaoNumPorDia = findViewById(R.id.refeicao_dia);
        refeicaoValorTotal = findViewById(R.id.total_refeicoes);

        hospedagemCustoNoite = findViewById(R.id.custo_noite);
        hospedagemNumNoites = findViewById(R.id.total_noites);
        hospedagemNumQuartos = findViewById(R.id.total_quartos);
        hospedagemValorTotal = findViewById(R.id.total_hospedagem);


        CarregarDadosViagem();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void CarregarDadosViagem() {
        if (viagem == null) return;

        principalOrigem.setText("Origem: " + viagem.getPrincipalOrigem());
        principalDestino.setText("Destino: " + viagem.getPrincipalDestino());
        principalDuracao.setText("Duração: " + viagem.getPrincipalDuracaoDias() + " dias");
        principalNumViajantes.setText("Número de viajantes: " + viagem.getPrincipalNumViajantes());
        principalValorTotalViagem.setText("Valor total da viagem: " + String.format("%.2f", viagem.getCustoTotal()) + " reais");

        combustivelTotalKm.setText("Distância estimada em Km: " + viagem.getCombustivelDistanciaTotalKm() + " km");
        combustivelMediaLitro.setText("Média de Km por litro: " + viagem.getCombustivelMediaKmLitro() + " km/L");
        combustivelCustoLitro.setText("Custo por litro: " + String.format("%.2f", viagem.getCombustivelCustoMedioLitro()) + " reais");
        combustivelTotalVeiculos.setText("Total de veículos: " + viagem.getCombustivelNumVeiculos());
        combustivelValorTotal.setText("Valor total: " + String.format("%.2f", viagem.getCustoCombustivel()) + " reais");

        if (viagem.possuiPassagemAerea()) {
            tarifaAereaCustoPessoa.setText("Custo por pessoa: " + String.format("%.2f", viagem.getTarifaAereaCustoPessoa()) + " reais");
            tarifaAereaAluguelVeiculo.setText("Aluguel do veículo: " + String.format("%.2f", viagem.getTarifaAereaCustoAluguelVeiculo()) + " reais");
            tarifaAereaValorTotal.setText("Valor total: " + String.format("%.2f", viagem.getCustoTarifaAerea()) + " reais");
        }

        refeicaoCustoPessoa.setText("Custo por refeição: " + String.format("%.2f", viagem.getRefeicaoCustoMedio()) + " reais");
        refeicaoNumPorDia.setText("Número de refeições por dia: " + viagem.getRefeicaoPorDia());
        refeicaoValorTotal.setText("Valor total: " + String.format("%.2f", viagem.getCustoRefeicoes()) + " reais");

        if (viagem.possuiHospedagem()) {
            hospedagemCustoNoite.setText("Custo médio por noite: " + String.format("%.2f", viagem.getHospedagemCustoMedioNoite()) + " reais");
            hospedagemNumNoites.setText("Total de noites: " + viagem.getHospedagemTotalNoites());
            hospedagemValorTotal.setText("Total de quartos: " + viagem.getHospedagemTotalQuartos());
            hospedagemNumQuartos.setText("Valor total: " + String.format("%.2f", viagem.getCustoHospedagem()) + " reais");
        }

        if (viagem.getCustosAdicionais().isEmpty()) return;

        CriarSecaoCustosAdicionais();
    }

    private void CriarSecaoCustosAdicionais() {
        // Create the LinearLayout
        LinearLayout linearLayout = new LinearLayout(VisualizarViagemActivity.this);
        linearLayout.setId(View.generateViewId());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Create the TextView
        TextView tituloTextView = new TextView(VisualizarViagemActivity.this);
        tituloTextView.setId(View.generateViewId());
        tituloTextView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tituloTextView.setBackgroundColor(ContextCompat.getColor(VisualizarViagemActivity.this, R.color.verde));
        tituloTextView.setPadding(layoutHelper.dpToPx(8), layoutHelper.dpToPx(8), layoutHelper.dpToPx(8), layoutHelper.dpToPx(8));
        tituloTextView.setText(R.string.custos_adicionais);
        tituloTextView.setTextColor(ContextCompat.getColor(VisualizarViagemActivity.this, R.color.begeclaro));
        tituloTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        tituloTextView.setTypeface(null, android.graphics.Typeface.BOLD);

        // Add the TextView to the LinearLayout
        linearLayout.addView(tituloTextView);

        listagem.addView(linearLayout);

        CriarCustosAdicionais(linearLayout);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void CriarCustosAdicionais(LinearLayout layout) {
        for (ViagemCustoAdicionalModel custo : viagem.getCustosAdicionais()) {
            TextView custoAdicionalItemTextView = new TextView(VisualizarViagemActivity.this);
            custoAdicionalItemTextView.setId(View.generateViewId());
            custoAdicionalItemTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            custoAdicionalItemTextView.setPadding(layoutHelper.dpToPx(8), layoutHelper.dpToPx(8), layoutHelper.dpToPx(8), layoutHelper.dpToPx(8));
            custoAdicionalItemTextView.setHint(R.string.descricao_preco);
            custoAdicionalItemTextView.setSingleLine(true);
            custoAdicionalItemTextView.setHorizontallyScrolling(true);
            custoAdicionalItemTextView.setTextColor(ContextCompat.getColor(VisualizarViagemActivity.this, R.color.cinzae));
            custoAdicionalItemTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            custoAdicionalItemTextView.setText(custo.getDescricao() + ": " + String.format("%.2f", custo.getCusto()) + " reais");

            layout.addView(custoAdicionalItemTextView);
        }

        // Create the second TextView
        TextView totalCustosAdicionaisTextView = new TextView(VisualizarViagemActivity.this);
        totalCustosAdicionaisTextView.setId(View.generateViewId());
        totalCustosAdicionaisTextView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        totalCustosAdicionaisTextView.setPadding(layoutHelper.dpToPx(8), layoutHelper.dpToPx(8), layoutHelper.dpToPx(8), layoutHelper.dpToPx(8));
        totalCustosAdicionaisTextView.setHint(R.string.valor_total_nao_informado);
        totalCustosAdicionaisTextView.setTextColor(ContextCompat.getColor(VisualizarViagemActivity.this, R.color.cinzae));
        totalCustosAdicionaisTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        totalCustosAdicionaisTextView.setText("Valor total: " + String.format("%.2f", viagem.getCustoGastosAdicionais()) + " reais");

        // Add the TextViews to the LinearLayout
        layout.addView(totalCustosAdicionaisTextView);
    }

    private void Voltar() {
        sharedHelper.ClearViagem();
        Intent intent = new Intent(VisualizarViagemActivity.this, ViagensActivity.class);
        startActivity(intent);
    }

    private void Editar() {
        sharedHelper.SetString(SharedHelper.ViagemPrincipalOrigem, viagem.getPrincipalOrigem());
        sharedHelper.SetString(SharedHelper.ViagemPrincipalDestino, viagem.getPrincipalDestino());
        sharedHelper.SetInt(SharedHelper.ViagemPrincipalDuracaoDias, viagem.getPrincipalDuracaoDias());
        sharedHelper.SetInt(SharedHelper.ViagemPrincipalNumViajantes, viagem.getPrincipalNumViajantes());
        sharedHelper.SetBoolean(SharedHelper.ViagemUtilizaPassagemAerea, viagem.possuiPassagemAerea());
        sharedHelper.SetBoolean(SharedHelper.ViagemUtilizaHospedagem, viagem.possuiHospedagem());

        sharedHelper.SetInt(SharedHelper.ViagemCombustivelDistanciaKm, viagem.getCombustivelDistanciaTotalKm());
        sharedHelper.SetFloat(SharedHelper.ViagemCombustivelKmLitro, viagem.getCombustivelMediaKmLitro());
        sharedHelper.SetFloat(SharedHelper.ViagemCombustivelCustoLitro, viagem.getCombustivelCustoMedioLitro());
        sharedHelper.SetInt(SharedHelper.ViagemCombustivelNumVeiculos, viagem.getCombustivelNumVeiculos());
        sharedHelper.SetFloat(SharedHelper.ViagemValorTotalCombustivel, viagem.getCustoCombustivel());

        if (viagem.possuiPassagemAerea()) {
            sharedHelper.SetFloat(SharedHelper.ViagemTarifaAereaCustoAluguelVeiculo, viagem.getTarifaAereaCustoAluguelVeiculo());
            sharedHelper.SetFloat(SharedHelper.ViagemTarifaAereaCustoPorPessoa, viagem.getTarifaAereaCustoPessoa());
            sharedHelper.SetFloat(SharedHelper.ViagemValorTotalTarifaAerea, viagem.getCustoTarifaAerea());
        }

        sharedHelper.SetInt(SharedHelper.ViagemRefeicaoNumPorDia, viagem.getRefeicaoPorDia());
        sharedHelper.SetFloat(SharedHelper.ViagemRefeicaoCustoMedio, viagem.getRefeicaoCustoMedio());
        sharedHelper.SetFloat(SharedHelper.ViagemValorTotalRefeicao, viagem.getCustoRefeicoes());

        if (viagem.possuiHospedagem()) {
            sharedHelper.SetFloat(SharedHelper.ViagemHospedagemCustoNoite, viagem.getHospedagemCustoMedioNoite());
            sharedHelper.SetInt(SharedHelper.ViagemHospedagemNumQuartos, viagem.getHospedagemTotalQuartos());
            sharedHelper.SetInt(SharedHelper.ViagemHospedagemNumNoites, viagem.getHospedagemTotalNoites());
            sharedHelper.SetFloat(SharedHelper.ViagemValorTotalHospedagem, viagem.getCustoHospedagem());
        }

        Intent intent = new Intent(VisualizarViagemActivity.this, PrincipalActivity.class);
        startActivity(intent);
    }

    private void Enviar() {
        GenialSaudeViagem gsViagem = new GenialSaudeViagem();
        gsViagem.setId(viagem.getId());
        gsViagem.setTotalViajantes(viagem.getPrincipalNumViajantes());
        gsViagem.setDuracaoViagem(viagem.getPrincipalDuracaoDias());
        gsViagem.setCustoTotalViagem(viagem.getCustoTotal());
        gsViagem.setCustoPorPessoa(viagem.getCustoTotal() / viagem.getPrincipalNumViajantes());
        gsViagem.setLocal(viagem.getPrincipalOrigem() + " - " + viagem.getPrincipalDestino());

        GenialSaudeViagemCustoRefeicao gsViagemRefeicao = new GenialSaudeViagemCustoRefeicao();
        gsViagemRefeicao.setViagemId(viagem.getId());
        gsViagemRefeicao.setCustoRefeicao(viagem.getRefeicaoCustoMedio());
        gsViagemRefeicao.setRefeicoesDia(viagem.getRefeicaoPorDia());
        gsViagem.setRefeicao(gsViagemRefeicao);

        GenialSaudeViagemCustoGasolina gsViagemGasolina = new GenialSaudeViagemCustoGasolina();
        gsViagemGasolina.setViagemId(viagem.getId());
        gsViagemGasolina.setTotalEstimadoKM(viagem.getCombustivelDistanciaTotalKm());
        gsViagemGasolina.setMediaKMLitro(viagem.getCombustivelMediaKmLitro());
        gsViagemGasolina.setCustoMedioLitro(viagem.getCombustivelCustoMedioLitro());
        gsViagemGasolina.setTotalVeiculos(viagem.getCombustivelNumVeiculos());
        gsViagem.setGasolina(gsViagemGasolina);

        if (viagem.possuiHospedagem()) {
            GenialSaudeViagemCustoHospedagem gsViagemHospedagem = new GenialSaudeViagemCustoHospedagem();
            gsViagemHospedagem.setViagemId(viagem.getId());
            gsViagemHospedagem.setCustoMedioNoite(viagem.getHospedagemCustoMedioNoite());
            gsViagemHospedagem.setTotalNoite(viagem.getHospedagemTotalNoites());
            gsViagemHospedagem.setTotalQuartos(viagem.getHospedagemTotalQuartos());
            gsViagem.setHospedagem(gsViagemHospedagem);
        }

        if (viagem.possuiPassagemAerea()) {
            GenialSaudeViagemCustoAereo gsViagemAereo = new GenialSaudeViagemCustoAereo();
            gsViagemAereo.setViagemId(viagem.getId());
            gsViagemAereo.setCustoPessoa(viagem.getTarifaAereaCustoPessoa());
            gsViagemAereo.setCustoAluguelVeiculo(viagem.getTarifaAereaCustoAluguelVeiculo());
            gsViagem.setAereo(gsViagemAereo);
        }

        ArrayList<GenialSaudeViagemCustoEntretenimento> listaEntretenimento = new ArrayList<>();

        for (ViagemCustoAdicionalModel custo : viagem.getCustosAdicionais()) {
            GenialSaudeViagemCustoEntretenimento entretenimento = new GenialSaudeViagemCustoEntretenimento();
            entretenimento.setViagemId(viagem.getId());
            entretenimento.setEntretenimento(custo.getDescricao());
            entretenimento.setValor(custo.getCusto());
            listaEntretenimento.add(entretenimento);
        }

        GenialSaudeViagemCustoEntretenimento[] array = listaEntretenimento.toArray(new GenialSaudeViagemCustoEntretenimento[0]);

        gsViagem.setListaEntretenimento(array);

        GenialSaudeApiClient.postViagem(gsViagem, new Callback<GenialSaudeResponse>() {
            @Override
            public void onResponse(Call<GenialSaudeResponse> call, Response<GenialSaudeResponse> response) {
                if (response.code() == 200) {
                    alertHelper.CriarAlerta("Sucesso", "Viagem integrada com Genial Saúde");
                } else {
                    alertHelper.CriarAlerta("Erro", "Integração com Genial Saúde retornou erro com código " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GenialSaudeResponse> call, Throwable t) {
                alertHelper.CriarAlerta("Erro", "Erro interno ao integrar viagem com Genial Saúde");
            }
        });
    }
}
