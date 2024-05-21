package com.example.viajada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viajada.database.dao.ViagemDao;
import com.example.viajada.helper.AlertHelper;
import com.example.viajada.helper.SharedHelper;

public class VisualizarViagemActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    private AlertHelper alertHelper;
    private Button btnSair, btnNovaViagem;
    private LinearLayout listagem;
    private ViagemDao viagemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        sharedHelper = new SharedHelper(VisualizarViagemActivity.this);
        alertHelper = new AlertHelper(VisualizarViagemActivity.this);
        btnSair = findViewById(R.id.sair_btn);
        btnNovaViagem = findViewById(R.id.nova_viagem_btn);
        listagem = (LinearLayout) findViewById(R.id.listagem);
        viagemDao = new ViagemDao(VisualizarViagemActivity.this);

        ListarViagens();

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedHelper.Clear();

                Intent intent = new Intent(VisualizarViagemActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnNovaViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedHelper.ClearViagem();

                Intent intent = new Intent(VisualizarViagemActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ListarViagens() {

    }
}