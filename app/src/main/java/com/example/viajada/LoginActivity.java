package com.example.viajada;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText usuario, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loginButton = findViewById(R.id.login_button);
        usuario = findViewById(R.id.usuario);
        senha = findViewById(R.id.senha);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr = usuario.getText().toString();
                String pwd = senha.getText().toString();

                if (usr.isEmpty()) {
                    CreateAlert("Aviso", "Preencha o campo Usuário");
                    return;
                }
                if (pwd.isEmpty()) {
                    CreateAlert("Aviso", "Preencha o campo Senha");
                    return;
                }

                if (usr.equals("Administrador") && pwd.equals("Administrador") ||
                        usr.equals("Administrador") && pwd.equals("pr4frentef0rever") ||
                        usr.equals("Adm") && pwd.equals("Adm123") ||
                        usr.equals("Root") && pwd.equals("Que3B1eng4ElT0r0")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    CreateAlert("Falha", "Cadastro não encontrado");
                }
            }
        });
    }

    private void CreateAlert(String title, String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

}