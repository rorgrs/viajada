package com.example.viajada;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viajada.database.dao.UsuarioDao;
import com.example.viajada.database.model.UsuarioModel;
import com.example.viajada.helper.AlertHelper;
import com.example.viajada.helper.SharedHelper;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnCadastrar;
    private EditText inputNomeUsuario, inputSenha;
    private UsuarioDao usuarioDao;
    private AlertHelper alertHelper;
    private SharedHelper sharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        usuarioDao = new UsuarioDao(LoginActivity.this);
        alertHelper = new AlertHelper(LoginActivity.this);
        sharedHelper = new SharedHelper(LoginActivity.this);

        sharedHelper.Clear();

        btnLogin = findViewById(R.id.login);
        btnCadastrar = findViewById(R.id.cadastro);
        inputNomeUsuario = findViewById(R.id.nome_usuario);
        inputSenha = findViewById(R.id.senha);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr = inputNomeUsuario.getText().toString();
                String pwd = inputSenha.getText().toString();

                UsuarioModel usuario = usuarioDao.ConsultarPorNomeSenha(usr, pwd);

                if(usuario == null){
                    alertHelper.CriarAlerta("Usuário não encontrado", "Revise seus dados de login e tente novamente");
                    return;
                }

                sharedHelper.SetLong(SharedHelper.UsuarioId, usuario.getId());

                Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastrarActivity.class);
                startActivity(intent);
            }
        });



    }
}