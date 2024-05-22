package com.example.viajada;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.viajada.database.dao.ViagemCustoAdicionalDao;
import com.example.viajada.database.dao.ViagemDao;
import com.example.viajada.database.model.ViagemCustoAdicionalModel;
import com.example.viajada.database.model.ViagemModel;
import com.example.viajada.helper.AlertHelper;
import com.example.viajada.helper.LayoutHelper;
import com.example.viajada.helper.SharedHelper;

import java.util.ArrayList;

public class CustosAdicionaisActivity extends AppCompatActivity {

    private SharedHelper sharedHelper;
    private AlertHelper alertHelper;
    private LayoutHelper layoutHelper;
    private Button btnX, btnAddCustoAdicional, btnFinalizar, btnVoltar;
    private TextView viewValorTotal;
    private LinearLayout listagem;
    private ViagemCustoAdicionalDao custoAdicionalDao;
    private ViagemDao viagemDao;
    private ArrayList<ViagemCustoAdicionalModel> custosAdicionais = new ArrayList<ViagemCustoAdicionalModel>();
    private ArrayList<LinearLayout> custosAdicionaisLinearLayouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custos_adicionais);

        sharedHelper = new SharedHelper(CustosAdicionaisActivity.this);
        alertHelper = new AlertHelper(CustosAdicionaisActivity.this);
        layoutHelper = new LayoutHelper(CustosAdicionaisActivity.this);

        btnX = findViewById(R.id.x_btn);
        btnVoltar = findViewById(R.id.voltar_btn);
        btnFinalizar = findViewById(R.id.finalizar_btn);
        btnAddCustoAdicional = findViewById(R.id.add_custoAdicional_btn);
        viewValorTotal = findViewById(R.id.total);
        listagem = (LinearLayout) findViewById(R.id.listagem);
        custoAdicionalDao = new ViagemCustoAdicionalDao(CustosAdicionaisActivity.this);
        viagemDao = new ViagemDao(CustosAdicionaisActivity.this);

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
                Finalizar();
            }
        });

        btnAddCustoAdicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustoAdicional(null);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void VerificarEdicaoViagem() {
        long idViagem = sharedHelper.GetLong(SharedHelper.ViagemId);

        if (idViagem == 0) return;

        ArrayList<ViagemCustoAdicionalModel> custos = custoAdicionalDao.ConsultarPorViagemId(idViagem);

        if (custos == null || custos.isEmpty()) return;

        for (ViagemCustoAdicionalModel custo : custos) {
            AddCustoAdicional(custo);
        }
    }

    @SuppressLint("DefaultLocale")
    private void VerificarValorTotal() {
        float valorTotalCombustivel = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalCombustivel);
        float valorTotalTarifaAerea = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalTarifaAerea);
        float valorTotalHospedagem = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalHospedagem);
        float valorTotalRefeicao = sharedHelper.GetFloat(SharedHelper.ViagemValorTotalRefeicao);
        float valorTotalCustosAdicionais = CalcularValorTotalTela();

        float valorTotal = valorTotalCombustivel + valorTotalTarifaAerea + valorTotalHospedagem + valorTotalRefeicao + valorTotalCustosAdicionais;

        viewValorTotal.setText(String.format("%.2f", valorTotal));
    }

    private float CalcularValorTotalTela() {
        AtualizarDadosDosCustos();

        if (custosAdicionais.isEmpty()) return 0;

        float soma = 0;

        for (ViagemCustoAdicionalModel custo : custosAdicionais) {
            soma += custo.getCusto();
        }

        return soma;
    }

    private void Finalizar() {
        AtualizarDadosDosCustos();

        long idViagem = sharedHelper.GetLong(SharedHelper.ViagemId);

        ViagemModel viagem = new ViagemModel();

        viagem.setUsuarioId(sharedHelper.GetLong(SharedHelper.UsuarioId));

        viagem.setPrincipalDestino(sharedHelper.GetString(SharedHelper.ViagemPrincipalDestino));
        viagem.setPrincipalOrigem(sharedHelper.GetString(SharedHelper.ViagemPrincipalOrigem));
        viagem.setPrincipalNumViajantes(sharedHelper.GetInt(SharedHelper.ViagemPrincipalNumViajantes));
        viagem.setPrincipalDuracaoDias(sharedHelper.GetInt(SharedHelper.ViagemPrincipalDuracaoDias));

        viagem.setCombustivelCustoMedioLitro(sharedHelper.GetFloat(SharedHelper.ViagemCombustivelCustoLitro));
        viagem.setCombustivelMediaKmLitro(sharedHelper.GetFloat(SharedHelper.ViagemCombustivelKmLitro));
        viagem.setCombustivelDistanciaTotalKm(sharedHelper.GetInt(SharedHelper.ViagemCombustivelDistanciaKm));
        viagem.setCombustivelNumVeiculos(sharedHelper.GetInt(SharedHelper.ViagemCombustivelNumVeiculos));

        if (sharedHelper.GetBoolean(SharedHelper.ViagemUtilizaPassagemAerea)) {
            viagem.setTarifaAereaCustoPessoa(sharedHelper.GetFloat(SharedHelper.ViagemTarifaAereaCustoPorPessoa));
            viagem.setTarifaAereaCustoAluguelVeiculo(sharedHelper.GetFloat(SharedHelper.ViagemTarifaAereaCustoAluguelVeiculo));
        }

        if (sharedHelper.GetBoolean(SharedHelper.ViagemUtilizaHospedagem)) {
            viagem.setHospedagemTotalNoites(sharedHelper.GetInt(SharedHelper.ViagemHospedagemNumNoites));
            viagem.setHospedagemTotalQuartos(sharedHelper.GetInt(SharedHelper.ViagemHospedagemNumQuartos));
            viagem.setHospedagemCustoMedioNoite(sharedHelper.GetFloat(SharedHelper.ViagemHospedagemCustoNoite));
        }

        viagem.setRefeicaoCustoMedio(sharedHelper.GetFloat(SharedHelper.ViagemRefeicaoCustoMedio));
        viagem.setRefeicaoPorDia(sharedHelper.GetInt(SharedHelper.ViagemRefeicaoNumPorDia));

        viagem.setCustosAdicionais(custosAdicionais);

        if(idViagem != 0) viagem.setId(idViagem);

        try {
            if(idViagem != 0) {
                viagem.setId(idViagem);
                viagemDao.Atualizar(viagem);
            } else {
                viagemDao.Inserir(viagem);
            }
        } catch (Exception e) {
            alertHelper.CriarAlerta("Erro", "Ocorreu um erro ao salvar a viagem.");
            return;
        }

        sharedHelper.ClearViagem();

        Toast.makeText(this, "Viagem cadastrada com sucesso", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CustosAdicionaisActivity.this, ViagensActivity.class);
        startActivity(intent);
    }

    @SuppressLint("DefaultLocale")
    private void AddCustoAdicional(ViagemCustoAdicionalModel custo) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        // Set layout margins for the LinearLayout
        LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        linearLayoutParams.setMargins(layoutHelper.dpToPx(10), layoutHelper.dpToPx(30), 0, 0);
        linearLayout.setLayoutParams(linearLayoutParams);

        // Create the first EditText
        EditText descricaoEditText = new EditText(this);
        descricaoEditText.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                layoutHelper.dpToPx(50),
                3
        ));
        descricaoEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_textview));
        descricaoEditText.setGravity(Gravity.CENTER);
        descricaoEditText.setSingleLine(true);
        descricaoEditText.setHorizontallyScrolling(true);
        if (custo != null) descricaoEditText.setText(custo.getDescricao());
        else descricaoEditText.setText(R.string.digite_aqui);
        descricaoEditText.setTextColor(Color.WHITE);

        // Create the second EditText
        EditText precoEditText = new EditText(this);
        precoEditText.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                layoutHelper.dpToPx(50),
                3
        ));
        precoEditText.setHint(R.string.reais);
        precoEditText.setTextColor(Color.BLACK);
        if (custo != null) precoEditText.setText(String.format("%.2f", custo.getCusto()));

        precoEditText.addTextChangedListener(new TextWatcher() {
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

        // Create the Button
        Button excluirButton = new Button(this);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0,
                layoutHelper.dpToPx(50),
                1
        );

        buttonParams.setMarginEnd(layoutHelper.dpToPx(10));
        excluirButton.setLayoutParams(buttonParams);
        excluirButton.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_botao));
        excluirButton.setText(R.string.x);
        excluirButton.setTextColor(Color.WHITE);

        excluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the linearLayout from its parent
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
                custosAdicionaisLinearLayouts.remove(linearLayout);
            }
        });

        // Add views to the LinearLayout
        linearLayout.addView(descricaoEditText);
        linearLayout.addView(precoEditText);
        linearLayout.addView(excluirButton);

        // Add the LinearLayout to the parent layout
        listagem.addView(linearLayout);
        custosAdicionaisLinearLayouts.add(linearLayout);
    }

    private void AtualizarDadosDosCustos() {
        custosAdicionais.clear();
        for (LinearLayout linearLayout : custosAdicionaisLinearLayouts) {
            try {
                EditText descricaoEditText = (EditText) linearLayout.getChildAt(0);
                EditText precoEditText = (EditText) linearLayout.getChildAt(1);
                String descricao = descricaoEditText.getText().toString();
                float preco = Float.parseFloat(precoEditText.getText().toString());
                ViagemCustoAdicionalModel custo = new ViagemCustoAdicionalModel();
                custo.setCusto(preco);
                custo.setDescricao(descricao);
                custosAdicionais.add(custo);
            } catch (Exception e) {
                continue;
            }
        }
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
        boolean hospedagem = sharedHelper.GetBoolean(SharedHelper.ViagemUtilizaHospedagem);
        Intent intent = new Intent(CustosAdicionaisActivity.this, hospedagem ? HospedagemActivity.class : RefeicoesActivity.class);
        startActivity(intent);
    }
}