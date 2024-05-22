package com.example.viajada;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.viajada.database.dao.ViagemDao;
import com.example.viajada.database.model.ViagemModel;
import com.example.viajada.helper.LayoutHelper;
import com.example.viajada.helper.SharedHelper;

import java.util.ArrayList;

public class ViagensActivity extends AppCompatActivity {

    private SharedHelper sharedHelper;
    private LayoutHelper layoutHelper;
    private ViagemDao viagemDao;

    private LinearLayout listagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens);

        viagemDao = new ViagemDao(ViagensActivity.this);
        layoutHelper = new LayoutHelper(ViagensActivity.this);
        sharedHelper = new SharedHelper(ViagensActivity.this);
        viagemDao = new ViagemDao(ViagensActivity.this);
        listagem = findViewById(R.id.listagem_viagens);

        // Botão Nova Viagem
        Button novaViagemBtn = findViewById(R.id.nova_viagem_btn);

        novaViagemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedHelper.ClearViagem();
                Intent intent = new Intent(ViagensActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });

        Button sairBtn = findViewById(R.id.sair_btn);
        sairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedHelper.Clear();
                Intent intent = new Intent(ViagensActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<ViagemModel> viagens = viagemDao.ListarTodas(sharedHelper.GetLong(SharedHelper.UsuarioId));

        for (ViagemModel viagem : viagens) {
            CriarItemListagem(viagem);
        }
    }

    private void CriarItemListagem(ViagemModel viagem) {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, layoutHelper.dpToPx(20), 0, layoutHelper.dpToPx(8));
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(layoutHelper.dpToPx(8), layoutHelper.dpToPx(8), layoutHelper.dpToPx(8), layoutHelper.dpToPx(8));
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.verde));

        TextView viagemOrigemDestino = new TextView(this);
        viagemOrigemDestino.setId(R.id.viagem_origem_destino);
        viagemOrigemDestino.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        viagemOrigemDestino.setText(viagem.getPrincipalOrigem() + " - " + viagem.getPrincipalDestino());
        viagemOrigemDestino.setTextSize(18f);
        viagemOrigemDestino.setTypeface(viagemOrigemDestino.getTypeface(), android.graphics.Typeface.BOLD);
        viagemOrigemDestino.setPadding(0, 0, 0, layoutHelper.dpToPx(4));
        viagemOrigemDestino.setTextColor(Color.WHITE);

        TextView viagemDuracao = new TextView(this);
        viagemDuracao.setId(R.id.viagem_duracao);
        viagemDuracao.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        viagemDuracao.setText(viagem.getPrincipalDuracaoDias() + " dias");
        viagemDuracao.setTextSize(16f);
        viagemDuracao.setTextColor(Color.WHITE);

        TextView viagemNumeroViajantes = new TextView(this);
        viagemNumeroViajantes.setId(R.id.viagem_numero_viajantes);
        viagemNumeroViajantes.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        viagemNumeroViajantes.setText(viagem.getPrincipalNumViajantes());
        viagemNumeroViajantes.setTextSize(16f);
        viagemNumeroViajantes.setTextColor(Color.WHITE);

        TextView viagemValorTotal = new TextView(this);
        viagemValorTotal.setId(R.id.viagem_valor_total);
        viagemValorTotal.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        viagemValorTotal.setText(String.format("%.2f", viagem.getCustoTotal()) + " reais");
        viagemValorTotal.setTextSize(16f);
        viagemValorTotal.setPadding(0, 0, 0, layoutHelper.dpToPx(8));
        viagemValorTotal.setTextColor(Color.WHITE);

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER);

        Button deleteButton = new Button(this);
        deleteButton.setId(R.id.viagem_botao_excluir);
        LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        );
        deleteButtonParams.setMarginStart(layoutHelper.dpToPx(10));
        deleteButtonParams.setMarginEnd(layoutHelper.dpToPx(10));
        deleteButton.setLayoutParams(deleteButtonParams);
        deleteButton.setText(getString(R.string.excluir));
        deleteButton.setTextColor(Color.WHITE);
        deleteButton.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_botao));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup) layout.getParent()).removeView(layout);
                viagemDao.DeletarPorId(viagem.getId());
            }
        });

        Button viewButton = new Button(this);
        viewButton.setId(R.id.viagem_botao_visualizar);
        LinearLayout.LayoutParams viewButtonParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        );
        viewButtonParams.setMarginStart(layoutHelper.dpToPx(10));
        viewButtonParams.setMarginEnd(layoutHelper.dpToPx(10));
        viewButton.setLayoutParams(viewButtonParams);
        viewButton.setText(getString(R.string.ver_detalhes));
        viewButton.setTextColor(Color.WHITE);
        viewButton.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_botao));
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedHelper.ClearViagem();
                sharedHelper.SetLong(SharedHelper.ViagemId, viagem.getId());
                Intent intent = new Intent(ViagensActivity.this, VisualizarViagemActivity.class);
                startActivity(intent);
            }
        });

        buttonLayout.addView(deleteButton);
        buttonLayout.addView(viewButton);

        layout.addView(viagemOrigemDestino);
        layout.addView(viagemDuracao);
        layout.addView(viagemNumeroViajantes);
        layout.addView(viagemValorTotal);
        layout.addView(buttonLayout);

        listagem.addView(layout);
    }
}
