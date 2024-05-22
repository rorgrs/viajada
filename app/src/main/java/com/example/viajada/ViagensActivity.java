package com.example.viajada;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.viajada.database.dao.ViagemDao;
import com.example.viajada.database.model.ViagemModel;
import java.util.ArrayList;

public class ViagensActivity extends AppCompatActivity {

    private ViagemDao viagemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens);

        // Inicialize o DAO
        viagemDao = new ViagemDao(this);

        LinearLayout listagemViagensLayout = findViewById(R.id.listagem_viagens);

        // Botão Nova Viagem
        Button novaViagemBtn = findViewById(R.id.nova_viagem_btn);
        novaViagemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViagensActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });

        // Botão Sair
        Button sairBtn = findViewById(R.id.sair_btn);
        sairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // Obtenha as viagens do banco de dados
        ArrayList<ViagemModel> viagens = viagemDao.ListarTodas(usuario);

        // Iterando sobre a lista de viagens e criando dinamicamente os elementos de TextView para cada viagem
        for (ViagemModel viagem : viagens) {
            // Criando dinamicamente um novo LinearLayout que representará a viagem
            LinearLayout viagemLayout = new LinearLayout(this);
            viagemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            viagemLayout.setOrientation(LinearLayout.VERTICAL);
            viagemLayout.setPadding(8, 8, 8, 8);
            viagemLayout.setBackgroundColor(getResources().getColor(R.color.verde));

            // Adiciona os TextViews
            TextView origemDestinoTextView = new TextView(this);
            origemDestinoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            origemDestinoTextView.setText(viagem.getPrincipalOrigem() + " - " + viagem.getPrincipalDestino());
            origemDestinoTextView.setTextSize(18);
            origemDestinoTextView.setTypeface(null, Typeface.BOLD);
            origemDestinoTextView.setTextColor(getResources().getColor(R.color.white));
            viagemLayout.addView(origemDestinoTextView);

            TextView duracaoTextView = new TextView(this);
            duracaoTextView.setText(String.valueOf(viagem.getPrincipalDuracaoDias()) + " dias");
            origemDestinoTextView.setTextColor(getResources().getColor(R.color.white));
            origemDestinoTextView.setTextSize(16);
            viagemLayout.addView(duracaoTextView);

            TextView numViajantesTextView = new TextView(this);
            numViajantesTextView.setText(String.valueOf(viagem.getPrincipalNumViajantes()) + " viajantes");
            origemDestinoTextView.setTextColor(getResources().getColor(R.color.white));
            origemDestinoTextView.setTextSize(16);
            viagemLayout.addView(numViajantesTextView);

            TextView valorTotalTextView = new TextView(this);
            valorTotalTextView.setText(String.format("R$ %.2f", viagem.getCustoTotal()));
            origemDestinoTextView.setTextColor(getResources().getColor(R.color.white));
            origemDestinoTextView.setTextSize(16);
            viagemLayout.addView(valorTotalTextView);

            Button botaoExcluir = new Button(this);
            botaoExcluir.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            botaoExcluir.setText(getString(R.string.excluir));
            botaoExcluir.setTextColor(getResources().getColor(R.color.white));
            botaoExcluir.setBackgroundResource(R.drawable.shape_botao);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) botaoExcluir.getLayoutParams();
            params.setMargins(10, 1, 10, 1);
            botaoExcluir.setLayoutParams(params);
            botaoExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long viagemId = viagem.getId();
                    excluirViagem(viagemId);
                    listagemViagensLayout.removeView((LinearLayout) v.getParent());
                }
            });
            viagemLayout.addView(botaoExcluir);

            Button botaoEditar = new Button(this);
            botaoEditar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            botaoEditar.setText(getString(R.string.editar));
            botaoExcluir.setTextColor(getResources().getColor(R.color.white));
            botaoEditar.setBackgroundResource(R.drawable.shape_botao);
            LinearLayout.LayoutParams parames = (LinearLayout.LayoutParams) botaoEditar.getLayoutParams();
            parames.setMargins(10, 1, 10, 1);
            botaoEditar.setLayoutParams(parames);

            botaoEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long idViagem = viagem.getId();

                    Intent intent = new Intent(view.getContext(), VisualizarViagemActivity.class);

                    intent.putExtra("ID_VIAGEM", idViagem);

                    startActivity(intent);
                }
            });
            viagemLayout.addView(botaoEditar);

            listagemViagensLayout.addView(viagemLayout);
        }
    }
    private void excluirViagem(long viagemId) {
        ViagemDao viagemDao = new ViagemDao(this);
        viagemDao.DeletarPorId(viagemId);
    }
}
