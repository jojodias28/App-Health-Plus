package com.ibm.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.myfirstapp.data.ApiRepository;
import com.ibm.myfirstapp.data.remote.Response;
import com.ibm.myfirstapp.data.remote.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private String msg = "";
    private EditText etName,etSenha;
    private String email, senha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etSenha = findViewById(R.id.etSenha);
        TextView tvCadastrar = findViewById(R.id.tvCadastrar);



        Button botao = findViewById(R.id.button);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etName.getText().toString();
                senha = etSenha.getText().toString();

                if ( TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.warning)
                            .setMessage(R.string.fill_all)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(R.drawable.ic_alert)
                            .show();

                }else{
                    login(email, senha);
                }
            }
        });


        tvCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);

            }
        });

    }

    public void login(String email, String password){
        Call<Response> responseCall = ApiRepository.service().userLogin(new UserLogin(email, password));
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()){
                    if(email.equals(response.body().getEmail()) && password.equals(response.body().getPassword())){
                        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                        String name = response.body().getName();
                        intent.putExtra("KeyName", name);
                        Toast.makeText(getApplicationContext(), R.string.login_succesfully, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }


                }else {
                    dialog();

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                Toast.makeText(getApplicationContext(), R.string.login_unsuccesfully, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void enviar(){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

    }

    private void dialog(){
        new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.warning)
                .setMessage(R.string.incorrect_email_password)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                  }

                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}