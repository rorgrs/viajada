package com.example.viajada;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viajada.database.dao.UsuarioDao;
import com.example.viajada.database.model.UsuarioModel;
import com.example.viajada.helper.AlertHelper;
import com.example.viajada.helper.SharedHelper;

public class CadastrarActivity extends AppCompatActivity {

    private EditText inputNomeUsuario, inputSenha, inputConfirmarSenha;
    private Button btnVoltar, btnCadastrar;
    private UsuarioDao usuarioDao;
    private AlertHelper alertHelper;
    private SharedHelper sharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        usuarioDao = new UsuarioDao(CadastrarActivity.this);
        alertHelper = new AlertHelper(CadastrarActivity.this);
        sharedHelper = new SharedHelper(CadastrarActivity.this);

        inputNomeUsuario = findViewById(R.id.nome_usuario);
        inputSenha = findViewById(R.id.senha);
        inputConfirmarSenha = findViewById(R.id.confirmar_senha);
        btnVoltar = findViewById(R.id.voltar);
        btnCadastrar = findViewById(R.id.cadastrar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastrarActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = inputNomeUsuario.getText().toString();
                String pwd = inputSenha.getText().toString();
                String conf = inputConfirmarSenha.getText().toString();

                if(!pwd.equals(conf)){
                    alertHelper.CriarAlerta("Erro", "Confirmação de senha incorreta");
                    return;
                }

                if(usuarioDao.ExistePorNome(usr)){
                    alertHelper.CriarAlerta("Erro", "Já existe um usuário cadastrado com esse nome");
                    return;
                }

                UsuarioModel usuarioNovo = new UsuarioModel();
                usuarioNovo.setNome(usr);
                usuarioNovo.setSenha(pwd);

                long id = usuarioDao.Inserir(usuarioNovo);

                sharedHelper.SetLong(SharedHelper.UsuarioId, id);

                Toast.makeText(CadastrarActivity.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CadastrarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}