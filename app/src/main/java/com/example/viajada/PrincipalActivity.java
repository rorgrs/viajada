package com.example.viajada;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viajada.helper.AlertHelper;
import com.example.viajada.helper.SharedHelper;

public class PrincipalActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    private AlertHelper alertHelper;
    private Button btnX, btnCancelar, btnProximo;
    private EditText inputOrigem, inputDestino, inputDuracao, inputNumViajantes;
    private CheckBox checkPassagemAerea, checkHospedagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        sharedHelper = new SharedHelper(PrincipalActivity.this);
        alertHelper = new AlertHelper(PrincipalActivity.this);

        btnX = findViewById(R.id.x_btn);
        btnCancelar = findViewById(R.id.cancelar_btn);
        btnProximo = findViewById(R.id.proximo_btn);
        checkPassagemAerea = (CheckBox) findViewById(R.id.passagem_aerea);
        checkHospedagem = (CheckBox) findViewById(R.id.hospedagem);
        inputOrigem = findViewById(R.id.cidade_origem);
        inputDestino = findViewById(R.id.cidade_destino);
        inputDuracao = findViewById(R.id.duracao);
        inputNumViajantes = findViewById(R.id.numero_viajantes);

        VerificarEdicaoViagem();

        btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarAvisoCancelamento();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarAvisoCancelamento();
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarInformacoes();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void VerificarEdicaoViagem(){
        String origem = sharedHelper.GetString(SharedHelper.ViagemPrincipalOrigem);
        String destino = sharedHelper.GetString(SharedHelper.ViagemPrincipalDestino);
        int duracao = sharedHelper.GetInt(SharedHelper.ViagemPrincipalDuracaoDias);
        int viajantes = sharedHelper.GetInt(SharedHelper.ViagemPrincipalNumViajantes);
        boolean passagemAerea = sharedHelper.GetBoolean(SharedHelper.ViagemUtilizaPassagemAerea);
        boolean hospedagem = sharedHelper.GetBoolean(SharedHelper.ViagemUtilizaHospedagem);

        if(origem != null) inputOrigem.setText(origem);
        if(destino != null) inputDestino.setText(destino);
        if(duracao != 0) inputDuracao.setText(Integer.toString(duracao));
        if(viajantes != 0) inputNumViajantes.setText(Integer.toString(viajantes));
        checkPassagemAerea.setChecked(passagemAerea);
        checkHospedagem.setChecked(hospedagem);
    }

    private void SalvarInformacoes(){
        String origem = inputOrigem.getText().toString();
        String destino = inputDestino.getText().toString();
        String duracaoStr = inputDuracao.getText().toString();
        String viajantesStr = inputNumViajantes.getText().toString();
        boolean passagemAerea = checkPassagemAerea.isChecked();
        boolean hospedagem = checkHospedagem.isChecked();

        if(origem.isEmpty() || destino.isEmpty() || duracaoStr.isEmpty() || viajantesStr.isEmpty()){
            alertHelper.CriarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        int duracao = Integer.parseInt(duracaoStr);
        int viajantes = Integer.parseInt(viajantesStr);

        sharedHelper.SetString(SharedHelper.ViagemPrincipalOrigem, origem);
        sharedHelper.SetString(SharedHelper.ViagemPrincipalDestino, destino);
        sharedHelper.SetInt(SharedHelper.ViagemPrincipalDuracaoDias, duracao);
        sharedHelper.SetInt(SharedHelper.ViagemPrincipalNumViajantes, viajantes);
        sharedHelper.SetBoolean(SharedHelper.ViagemUtilizaPassagemAerea, passagemAerea);
        sharedHelper.SetBoolean(SharedHelper.ViagemUtilizaHospedagem, hospedagem);

        Proximo();
    }

    private void Proximo(){
        Intent intent = new Intent(PrincipalActivity.this, CombustivelActivity.class);
        startActivity(intent);
    }

    private void Cancelar() {
        sharedHelper.ClearViagem();
        Intent intent = new Intent(PrincipalActivity.this, ViagensActivity.class);
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
}