package com.ibm.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    private TextView nomeUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        nomeUsuario =  findViewById(R.id.tvUsuario);

        String name = getIntent().getStringExtra("KeyName");
        nomeUsuario.setText(name);


        Button btSair = findViewById(R.id.btSair);

        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ContactActivity.this, MainActivity.class);
                startActivity(intent);

                finish();

            }
        });


    }

}