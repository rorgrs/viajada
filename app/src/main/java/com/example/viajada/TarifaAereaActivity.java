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

public class TarifaAereaActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    private AlertHelper alertHelper;
    private Button btnX, btnVoltar, btnProximo;
    private TextView viewValorTotal;
    private EditText inputCustoPessoa, inputAluguelVeiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarifa_aerea);

        sharedHelper = new SharedHelper(TarifaAereaActivity.this);
        alertHelper = new AlertHelper(TarifaAereaActivity.this);

        btnX = findViewById(R.id.x_btn);
        btnVoltar = findViewById(R.id.voltar_btn);
        btnProximo = findViewById(R.id.proximo_btn);
        viewValorTotal = findViewById(R.id.total);
        inputCustoPessoa = findViewById(R.id.custo_pessoa);
        inputAluguelVeiculo = findViewById(R.id.aluguel_veiculo);

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
            }
        });

        inputCustoPessoa.addTextChangedListener(new TextWatcher() {
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

        inputAluguelVeiculo.addTextChangedListener(new TextWatcher() {
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
        float custoPessoa = sharedHelper.GetFloat(SharedHelper.ViagemTarifaAereaCustoPorPessoa);
        float aluguelVeiculo = sharedHelper.GetFloat(SharedHelper.ViagemTarifaAereaCustoAluguelVeiculo);

        if(custoPessoa != 0) inputCustoPessoa.setText(String.format("%.2f", custoPessoa));
        if(aluguelVeiculo != 0) inputAluguelVeiculo.setText(String.format("%.2f", aluguelVeiculo));
    }

    @SuppressLint("DefaultLocale")
    private void VerificarValorTotal(){
        float valorTotalCombustivel = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalCombustivel);
        float valorTotalTarifaAerea = CalcularValorTotalTela();
        float valorTotalHospedagem = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalHospedagem);
        float valorTotalRefeicao = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalRefeicao);
        float valorTotalGastosAdicionais = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalCustosAdicionais);

        float valorTotal = valorTotalCombustivel + valorTotalTarifaAerea + valorTotalHospedagem + valorTotalRefeicao + valorTotalGastosAdicionais;

        viewValorTotal.setText(String.format("%.2f", valorTotal));
    }

    private float CalcularValorTotalTela(){
        String custoPessoaStr = inputCustoPessoa.getText().toString();
        String aluguelVeiculoStr = inputAluguelVeiculo.getText().toString();

        if(custoPessoaStr.isEmpty() || aluguelVeiculoStr.isEmpty()){
            return 0;
        }

        int numViajantes = sharedHelper.GetInt(SharedHelper.ViagemPrincipalNumViajantes);
        float custoPessoa = Float.parseFloat(custoPessoaStr);
        float aluguelVeiculo = Float.parseFloat(aluguelVeiculoStr);

        return (numViajantes * custoPessoa) + aluguelVeiculo;
    }

    private void SalvarInformacoes(){
        String custoPessoaStr = inputCustoPessoa.getText().toString();
        String aluguelVeiculoStr = inputAluguelVeiculo.getText().toString();

        if(custoPessoaStr.isEmpty() || aluguelVeiculoStr.isEmpty()){
            alertHelper.CriarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        float custoPessoa = Float.parseFloat(custoPessoaStr);
        float aluguelVeiculo = Float.parseFloat(aluguelVeiculoStr);

        sharedHelper.SetFloat(SharedHelper.ViagemTarifaAereaCustoAluguelVeiculo, aluguelVeiculo);
        sharedHelper.SetFloat(SharedHelper.ViagemTarifaAereaCustoPorPessoa, custoPessoa);
        sharedHelper.SetFloat(SharedHelper.ViagemValorTotalTarifaAerea, CalcularValorTotalTela());
        Proximo();
    }

    private void Proximo(){
        Intent intent = new Intent(TarifaAereaActivity.this, RefeicoesActivity.class);
        startActivity(intent);
    }

    private void Cancelar() {
        sharedHelper.ClearViagem();
        Intent intent = new Intent(TarifaAereaActivity.this, ViagensActivity.class);
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
        Intent intent = new Intent(TarifaAereaActivity.this, CombustivelActivity.class);
        startActivity(intent);
    }
}