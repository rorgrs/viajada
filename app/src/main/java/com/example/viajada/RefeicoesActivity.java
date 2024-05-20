package com.example.viajada;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viajada.helper.AlertHelper;
import com.example.viajada.helper.SharedHelper;

public class RefeicoesActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    private AlertHelper alertHelper;
    private Button btnX, btnVoltar, btnProximo;
    private TextView viewValorTotal;
    private EditText inputCustoRefeicao, inputRefeicoesPorDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        sharedHelper = new SharedHelper(RefeicoesActivity.this);
        alertHelper = new AlertHelper(RefeicoesActivity.this);

        btnX = findViewById(R.id.x_btn);
        btnVoltar = findViewById(R.id.voltar_btn);
        btnProximo = findViewById(R.id.proximo_btn);
        viewValorTotal = findViewById(R.id.total);
        inputCustoRefeicao = findViewById(R.id.custo_refeicao);
        inputRefeicoesPorDia = findViewById(R.id.refeicao_dia);

        VerificarEdicaoViagem();
        VerificarValorTotal();

        btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarAvisoCancelamento();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voltar();
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarInformacoes();
                Proximo();
            }
        });

        inputRefeicoesPorDia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                VerificarValorTotal();
            }
        });

    }

    @SuppressLint("DefaultLocale")
    private void VerificarEdicaoViagem(){
        float custoRefeicao = sharedHelper.GetFloat(SharedHelper.ViagemRefeicaoCustoMedio);
        int refeicoesDia = sharedHelper.GetInt(SharedHelper.ViagemRefeicaoNumPorDia);

        if(refeicoesDia != 0) inputRefeicoesPorDia.setText(refeicoesDia);
        if(custoRefeicao != 0) inputCustoRefeicao.setText(String.format("%.2f", custoRefeicao));
    }

    @SuppressLint("DefaultLocale")
    private void VerificarValorTotal(){
        float valorTotalCombustivel = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalCombustivel);
        float valorTotalTarifaAerea = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalTarifaAerea);
        float valorTotalHospedagem = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalHospedagem);
        float valorTotalRefeicao = CalcularValorTotalTela();
        float valorTotalGastosAdicionais = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalGastosAdicionais);

        float valorTotal = valorTotalCombustivel + valorTotalTarifaAerea + valorTotalHospedagem + valorTotalRefeicao + valorTotalGastosAdicionais;

        viewValorTotal.setText(String.format("%.2f", valorTotal));
    }

    private float CalcularValorTotalTela(){
        String refeicoesPorDiaStr = inputRefeicoesPorDia.getText().toString();
        String custoRefeicaoStr = inputCustoRefeicao.getText().toString();

        if(refeicoesPorDiaStr.isEmpty() || custoRefeicaoStr.isEmpty()){
            return 0;
        }

        int numViajantes = sharedHelper.GetInt(SharedHelper.ViagemPrincipalNumViajantes);
        int duracaoViagem = sharedHelper.GetInt(SharedHelper.ViagemPrincipalDuracaoDias);
        int refeicoesPorDia = Integer.parseInt(refeicoesPorDiaStr);
        float custoRefeicao = Float.parseFloat(custoRefeicaoStr);

        return numViajantes * refeicoesPorDia * custoRefeicao * duracaoViagem;
    }

    private void SalvarInformacoes(){
        String refeicoesPorDiaStr = inputRefeicoesPorDia.getText().toString();
        String custoRefeicaoStr = inputCustoRefeicao.getText().toString();

        if(refeicoesPorDiaStr.isEmpty() || custoRefeicaoStr.isEmpty()){
            alertHelper.CriarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        int refeicoesPorDia = Integer.parseInt(refeicoesPorDiaStr);
        float custoRefeicao = Float.parseFloat(custoRefeicaoStr);

        sharedHelper.SetFloat(SharedHelper.ViagemRefeicaoNumPorDia, refeicoesPorDia);
        sharedHelper.SetFloat(SharedHelper.ViagemRefeicaoCustoMedio, custoRefeicao);
        sharedHelper.SetFloat(SharedHelper.ViagemValorTotalRefeicao, CalcularValorTotalTela());
    }

    private void Proximo(){
        boolean hospedagem = sharedHelper.GetBoolean(SharedHelper.ViagemUtilizaHospedagem);
        Intent intent = new Intent(RefeicoesActivity.this, hospedagem ? HospedagemActivity.class : GastosAdicionaisActivity.class);
        startActivity(intent);
    }

    private void Cancelar() {
        sharedHelper.ClearViagem();
        Intent intent = new Intent(RefeicoesActivity.this, ViagensActivity.class);
        startActivity(intent);
    }
    private void MostrarAvisoCancelamento() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Deseja mesmo cancelar o cadastro da viagem?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cancelar();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "No", do nothing
            }
        });

        builder.show();
    }

    private void Voltar() {
        boolean passagemAerea = sharedHelper.GetBoolean(SharedHelper.ViagemUtilizaPassagemAerea);
        Intent intent = new Intent(RefeicoesActivity.this, passagemAerea ? TarifaAereaActivity.class : CombustivelActivity.class);
        startActivity(intent);
    }
}