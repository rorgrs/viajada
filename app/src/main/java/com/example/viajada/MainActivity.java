package com.example.viajada;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.viajada.database.dao.ViagemDao;
import com.example.viajada.database.model.ViagemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button reservarTodas, liberar, salvar, mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9;
    private EditText mesaReserva;
    private HashMap<Button, Integer> mesas = new HashMap<Button, Integer>();
    private HashMap<Button, String> mesasNomes = new HashMap<Button, String>();
    private ArrayList<String> mesasReservadas = new ArrayList<String>();
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private ViagemDao viagemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //############################## exemplo de uso do banco de dados
        viagemDao = new ViagemDao(MainActivity.this); //inicia esse cara sempre nesse onCreate da activity

        ViagemModel viagem = new ViagemModel(); //criando nova viagem
        viagem.setRefeicaoPorDia(10); //bota o resto das parada etc
        viagemDao.Inserir(viagem); //adicionando a viagem (respeitar campos obrigatorios(que sao quase todos))

        List<ViagemModel> viagens = viagemDao.ListarTodas(); //pega todas as viagens do banco
        //o jeito certo é paginar esse retorno mas fodase tamo na quinta fase vamo retornar todas e explodir o celular

        viagemDao.DeletarPorId(viagem.getId()); //deletando viagem (esse metodo vai deletar os custos adicionais tb pq se nao vai da ruim)

        ViagemModel algumaViagem = viagemDao.ConsultarPorId(viagem.getId());
        //############################## exemplo de uso do banco de dados

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = prefs.edit();
        liberar = findViewById(R.id.botao_liberar_mesa);
        salvar = findViewById(R.id.botao_salvar_operacao);
        mesaReserva = findViewById(R.id.numero_mesa);
        reservarTodas = findViewById(R.id.botao_reservar_todas);

        liberar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiberarMesa();
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarOperacao();
            }
        });

        reservarTodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservarTodas();
            }
        });

        mesa1 = findViewById(R.id.botao_mesa_1);
        mesa2 = findViewById(R.id.botao_mesa_2);
        mesa3 = findViewById(R.id.botao_mesa_3);
        mesa4 = findViewById(R.id.botao_mesa_4);
        mesa5 = findViewById(R.id.botao_mesa_5);
        mesa6 = findViewById(R.id.botao_mesa_6);
        mesa7 = findViewById(R.id.botao_mesa_7);
        mesa8 = findViewById(R.id.botao_mesa_8);
        mesa9 = findViewById(R.id.botao_mesa_9);

        mesas.put(mesa1, R.id.mesa1);
        mesas.put(mesa2, R.id.mesa2);
        mesas.put(mesa3, R.id.mesa3);
        mesas.put(mesa4, R.id.mesa4);
        mesas.put(mesa5, R.id.mesa5);
        mesas.put(mesa6, R.id.mesa6);
        mesas.put(mesa7, R.id.mesa7);
        mesas.put(mesa8, R.id.mesa8);
        mesas.put(mesa9, R.id.mesa9);

        mesasNomes.put(mesa1, "mesa1");
        mesasNomes.put(mesa2, "mesa2");
        mesasNomes.put(mesa3, "mesa3");
        mesasNomes.put(mesa4, "mesa4");
        mesasNomes.put(mesa5, "mesa5");
        mesasNomes.put(mesa6, "mesa6");
        mesasNomes.put(mesa7, "mesa7");
        mesasNomes.put(mesa8, "mesa8");
        mesasNomes.put(mesa9, "mesa9");

        for(Button mesa : mesas.keySet()){
            mesa.setActivated(true);
            mesa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReservarMesa(mesa);
                }
            });
        }

        CarregarReservas();
    }

    private void CarregarReservas(){
        for(Button mesa : mesasNomes.keySet()){
            String mesaNome = mesasNomes.get(mesa);
            boolean reservada = prefs.getBoolean(mesaNome, false);
            if(!reservada) continue;
            ReservarMesa(mesa);
        }
    }

    private void ReservarTodas(){
        boolean todasReservadas = true;
        for(Button mesa : mesasNomes.keySet()){
            String mesaNome = mesasNomes.get(mesa);
            if(mesa.isActivated()){
                todasReservadas = false;
            }
            ReservarMesa(mesa);
        }

        if(todasReservadas){
            CreateAlert("Aviso", "Operação inválida. Todas as mesas já possuem reserva");
        }
    }

    private void SalvarOperacao(){
        editor.apply();
        CreateAlert("Alerta", "Reservas salvas");
    }


    private void ResetarOperacao(){
        editor.clear();
        editor.commit();
    }

    private void LiberarMesa(){
        String mesaLabel = mesaReserva.getText().toString();
        mesaLabel = mesaLabel.replaceAll("\\D+","");
        String mesaNome = "mesa"+mesaLabel;
        if(!mesasNomes.containsValue(mesaNome)){
            CreateAlert("Alerta", "Mesa não encontrada");
            return;
        }
        Button mesa = GetMesaFromNome(mesaNome);
        if(mesa == null) return;

        if(mesa.isActivated()){
            CreateAlert("Alerta", "Mesa não reservada. A mesa "+mesaLabel+" encontra-se " +
                    "habilitada para reserva");
            return;
        }

        LinearLayout layoutMesa = findViewById(mesas.get(mesa));
        int cor = ContextCompat.getColor(MainActivity.this, R.color.secondary);
        layoutMesa.setBackgroundColor(cor);
        int corBotao = ContextCompat.getColor(MainActivity.this, R.color.primary);
        mesa.setActivated(true);
        mesa.setBackgroundColor(corBotao);
        String mn = mesasNomes.get(mesa);
        editor.putBoolean(mn, false);
    }

    private void ReservarMesa(Button mesa){
        LinearLayout layoutMesa = findViewById(mesas.get(mesa));
        int corReserva = ContextCompat.getColor(MainActivity.this, R.color.red);
        layoutMesa.setBackgroundColor(corReserva);
        int corBotao = ContextCompat.getColor(MainActivity.this, R.color.primaryLight);
        mesa.setActivated(false);
        mesa.setBackgroundColor(corBotao);
        String mesaNome = mesasNomes.get(mesa);
        editor.putBoolean(mesaNome, true);
    }

    private void CreateAlert(String title, String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private Button GetMesaFromNome(String nome){
        for (Map.Entry<Button, String> entry : mesasNomes.entrySet()) {
            if (entry.getValue().equals(nome)) {
                return entry.getKey();
            }
        }
        return null;
    }
}