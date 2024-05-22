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

public class CombustivelActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    private AlertHelper alertHelper;
    private Button btnX, btnVoltar, btnProximo;
    private TextView viewValorTotal;
    private EditText inputKmTotal, inputMediaKmLitro, inputCustoLitro, inputNumVeiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combustivel);

        sharedHelper = new SharedHelper(CombustivelActivity.this);
        alertHelper = new AlertHelper(CombustivelActivity.this);

        btnX = findViewById(R.id.x_btn);
        btnVoltar = findViewById(R.id.voltar_btn);
        btnProximo = findViewById(R.id.proximo_btn);
        viewValorTotal = findViewById(R.id.total);
        inputKmTotal = findViewById(R.id.km_total);
        inputMediaKmLitro = findViewById(R.id.km_media);
        inputCustoLitro = findViewById(R.id.custo_litro);
        inputNumVeiculos = findViewById(R.id.total_veiculos);

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

        inputNumVeiculos.addTextChangedListener(new TextWatcher() {
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
        int kmTotal = sharedHelper.GetInt(SharedHelper.ViagemCombustivelDistanciaKm);
        float kmLitro = sharedHelper.GetFloat(SharedHelper.ViagemCombustivelKmLitro);
        float custoLitro = sharedHelper.GetFloat(SharedHelper.ViagemCombustivelCustoLitro);
        int numVeiculos = sharedHelper.GetInt(SharedHelper.ViagemCombustivelNumVeiculos);

        if(kmTotal != 0) inputKmTotal.setText(kmTotal);
        if(kmLitro != 0) inputMediaKmLitro.setText(String.format("%.2f", kmLitro));
        if(custoLitro != 0) inputCustoLitro.setText(String.format("%.2f", custoLitro));
        if(numVeiculos != 0) inputNumVeiculos.setText(numVeiculos);
    }

    @SuppressLint("DefaultLocale")
    private void VerificarValorTotal() {
        float valorTotalCombustivel = CalcularValorTotalTela();
        float valorTotalTarifaAerea = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalTarifaAerea);
        float valorTotalHospedagem = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalHospedagem);
        float valorTotalRefeicao = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalRefeicao);
        float valorTotalGastosAdicionais = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalCustosAdicionais);

        float valorTotal = valorTotalCombustivel + valorTotalTarifaAerea + valorTotalHospedagem + valorTotalRefeicao + valorTotalGastosAdicionais;

        viewValorTotal.setText(String.format("%.2f", valorTotal));
    }

    private float CalcularValorTotalTela() {
        String kmTotalStr = inputKmTotal.getText().toString();
        String kmLitroStr = inputMediaKmLitro.getText().toString();
        String custoLitroStr = inputCustoLitro.getText().toString();
        String numVeiculosStr = inputNumVeiculos.getText().toString();

        if (kmTotalStr.isEmpty() || kmLitroStr.isEmpty() || custoLitroStr.isEmpty() || numVeiculosStr.isEmpty()) {
            return 0;
        }

        int kmTotal = Integer.parseInt(kmTotalStr);
        float kmLitro = Float.parseFloat(kmLitroStr);
        float custoLitro = Float.parseFloat(custoLitroStr);
        int numVeiculos = Integer.parseInt(numVeiculosStr);

        return (((float) kmTotal / kmLitro) * custoLitro) / numVeiculos;
    }

    private void SalvarInformacoes() {
        String kmTotalStr = inputKmTotal.getText().toString();
        String kmLitroStr = inputMediaKmLitro.getText().toString();
        String custoLitroStr = inputCustoLitro.getText().toString();
        String numVeiculosStr = inputNumVeiculos.getText().toString();

        if (kmTotalStr.isEmpty() || kmLitroStr.isEmpty() || custoLitroStr.isEmpty() || numVeiculosStr.isEmpty()) {
            alertHelper.CriarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        int kmTotal = Integer.parseInt(kmTotalStr);
        float kmLitro = Float.parseFloat(kmLitroStr);
        float custoLitro = Float.parseFloat(custoLitroStr);
        int numVeiculos = Integer.parseInt(numVeiculosStr);

        sharedHelper.SetInt(SharedHelper.ViagemCombustivelDistanciaKm, kmTotal);
        sharedHelper.SetFloat(SharedHelper.ViagemCombustivelKmLitro, kmLitro);
        sharedHelper.SetFloat(SharedHelper.ViagemCombustivelCustoLitro, custoLitro);
        sharedHelper.SetInt(SharedHelper.ViagemCombustivelNumVeiculos, numVeiculos);
        sharedHelper.SetFloat(SharedHelper.ViagemValorTotalCombustivel, CalcularValorTotalTela());
    }

    private void Proximo() {
        boolean passagemAerea = sharedHelper.GetBoolean(SharedHelper.ViagemUtilizaPassagemAerea);
        Intent intent = new Intent(CombustivelActivity.this, passagemAerea ? TarifaAereaActivity.class : RefeicoesActivity.class);
        startActivity(intent);
    }

    private void Cancelar() {
        sharedHelper.ClearViagem();
        Intent intent = new Intent(CombustivelActivity.this, ViagensActivity.class);
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
        Intent intent = new Intent(CombustivelActivity.this, PrincipalActivity.class);
        startActivity(intent);
    }
}