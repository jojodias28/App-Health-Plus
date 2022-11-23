package com.ibm.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ibm.myfirstapp.data.ApiRepository;
import com.ibm.myfirstapp.data.remote.Request;
import com.ibm.myfirstapp.data.remote.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha, etSenhaConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etEmail = findViewById(R.id.editEmail);
        etSenha = findViewById(R.id.editSenha);
        etSenhaConfirm = findViewById(R.id.editConfirmaSenha);
        etNome = findViewById(R.id.editNome);

        etEmail.setText(getIntent().getStringExtra("KeyEmail"));
        etSenha.setText(getIntent().getStringExtra("KeySenha"));


        Button btnCadastrar = findViewById(R.id.buttonCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                String confirmarSenha = etSenhaConfirm.getText().toString();
                String nome = etNome.getText().toString();

                if ( email.isEmpty() | senha.isEmpty() | nome.isEmpty() | confirmarSenha.isEmpty()){
                    new androidx.appcompat.app.AlertDialog.Builder(CadastroActivity.this)
                            .setTitle(R.string.warning)
                            .setMessage(R.string.fill_all)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(R.drawable.ic_alert)
                            .show();
                }else{
                    if( senha.equals(confirmarSenha) ) {

                          saveUser(createRequest());

                    }else{
                        alerta(R.string.divergin_passwords);
                    }

                }

           }

            private void alerta(int msg) {
                new AlertDialog.Builder(CadastroActivity.this)
                        .setTitle(R.string.warning)
                        .setMessage(msg)

                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });




    }
    public Request createRequest(){

        Request request = new Request();

        request.setName(etNome.getText().toString());
        request.setEmail(etEmail.getText().toString());
        request.setPassword(etSenha.getText().toString());

        return request;

    }
    public void saveUser(Request request){
        Call<Response> registerResponseCall = ApiRepository.service().saveUser(request);
        registerResponseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), R.string.successful,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroActivity.this, MainActivity.class );
                    startActivity(intent);
                    finish();
                }else{
                   dialog();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.wrong,Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void dialog(){
        new android.app.AlertDialog.Builder(CadastroActivity.this)
                .setTitle(R.string.warning)
                .setMessage(R.string.email_exists)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }

                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}