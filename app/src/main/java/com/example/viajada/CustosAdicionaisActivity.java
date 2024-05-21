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

public class CustosAdicionaisActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    private AlertHelper alertHelper;
    private Button btnX, btnAddCustoAdicional, btnFinalizar, btnVoltar;
    private TextView viewValorTotal;
    private EditText inputCustoNoite, inputTotalNoites, inputTotalQuartos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        sharedHelper = new SharedHelper(CustosAdicionaisActivity.this);
        alertHelper = new AlertHelper(CustosAdicionaisActivity.this);

        btnX = findViewById(R.id.x_btn);
        btnVoltar = findViewById(R.id.voltar_btn);
        btnFinalizar = findViewById(R.id.finalizar_btn);
        btnAddCustoAdicional = findViewById(R.id.add_custoAdicional_btn);
        viewValorTotal = findViewById(R.id.total);
        inputCustoNoite = findViewById(R.id.custo_noite);
        inputTotalNoites = findViewById(R.id.total_noites);
        inputTotalQuartos = findViewById(R.id.total_quartos);

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

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarInformacoes();
                Finalizar();
            }
        });

        btnAddCustoAdicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustoAdicional();
            }
        });

        inputTotalQuartos.addTextChangedListener(new TextWatcher() {
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
        float custoNoite = sharedHelper.GetFloat(SharedHelper.ViagemHospedagemCustoNoite);
        int totalNoites = sharedHelper.GetInt(SharedHelper.ViagemHospedagemNumNoites);
        int totalQuartos = sharedHelper.GetInt(SharedHelper.ViagemHospedagemNumQuartos);

        if(totalQuartos != 0) inputTotalQuartos.setText(totalQuartos);
        if(totalNoites != 0) inputTotalNoites.setText(totalNoites);
        if(custoNoite != 0) inputCustoNoite.setText(String.format("%.2f", custoNoite));
    }

    @SuppressLint("DefaultLocale")
    private void VerificarValorTotal(){
        float valorTotalCombustivel = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalCombustivel);
        float valorTotalTarifaAerea = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalTarifaAerea);
        float valorTotalHospedagem = CalcularValorTotalTela();
        float valorTotalRefeicao = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalRefeicao);

        float valorTotal = valorTotalCombustivel + valorTotalTarifaAerea + valorTotalHospedagem + valorTotalRefeicao;

        viewValorTotal.setText(String.format("%.2f", valorTotal));
    }

    private float CalcularValorTotalTela(){
        String custoNoiteStr = inputCustoNoite.getText().toString();
        String totalNoitesStr = inputTotalNoites.getText().toString();
        String totalQuartosStr = inputTotalQuartos.getText().toString();

        if(custoNoiteStr.isEmpty() || totalNoitesStr.isEmpty() || totalQuartosStr.isEmpty()){
            return 0;
        }

        int totalNoites = Integer.parseInt(totalNoitesStr);
        int totalQuartos = Integer.parseInt(totalQuartosStr);
        float custoNoite = Float.parseFloat(custoNoiteStr);

        return custoNoite * totalQuartos * totalNoites;
    }

    private void SalvarInformacoes(){
        String custoNoiteStr = inputCustoNoite.getText().toString();
        String totalNoitesStr = inputTotalNoites.getText().toString();
        String totalQuartosStr = inputTotalQuartos.getText().toString();

        if(custoNoiteStr.isEmpty() || totalNoitesStr.isEmpty() || totalQuartosStr.isEmpty()){
            alertHelper.CriarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        int totalNoites = Integer.parseInt(totalNoitesStr);
        int totalQuartos = Integer.parseInt(totalQuartosStr);
        float custoNoite = Float.parseFloat(custoNoiteStr);

        sharedHelper.SetFloat(SharedHelper.ViagemHospedagemCustoNoite, custoNoite);
        sharedHelper.SetInt(SharedHelper.ViagemHospedagemNumQuartos, totalQuartos);
        sharedHelper.SetInt(SharedHelper.ViagemHospedagemNumNoites, totalNoites);
        sharedHelper.SetFloat(SharedHelper.ViagemValorTotalHospedagem, CalcularValorTotalTela());
    }

    private void Finalizar(){

    }

    private void AddCustoAdicional(){

    }

    private void Cancelar() {
        sharedHelper.ClearViagem();
        Intent intent = new Intent(CustosAdicionaisActivity.this, ViagensActivity.class);
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
        Intent intent = new Intent(CustosAdicionaisActivity.this, PrincipalActivity.class);
        startActivity(intent);
    }
}