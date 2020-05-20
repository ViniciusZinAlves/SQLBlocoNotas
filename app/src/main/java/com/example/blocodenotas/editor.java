package com.example.blocodenotas;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class editor extends AppCompatActivity
{
        Nota nota;
        Button btnSalvar;
        EditText txtTitulo;
        EditText txtNota;
        int idNota = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        btnSalvar = (Button)findViewById  (R.id.btnsalvar    );
        txtTitulo = (EditText)findViewById(R.id.txtTituloNota);
        txtNota   = (EditText)findViewById(R.id.txtNota      );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalvar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                salvarNota();
            }
        });

        Intent intent = getIntent();

        idNota = intent.getIntExtra("notaId",-1);
        if(idNota!=-1){
            NotasDao notasDAO = new NotasDao(this);
            nota      = notasDAO.obterNota(idNota);
            txtTitulo.setText(nota.getTitulo());
            txtNota  .setText(nota.getTexto ());
        }
        else{
            nota = new Nota();
        }

    }

    public void salvarNota(){

        //Verifica se há texto digitado na caixa de texto
        if(txtNota.getText().toString().length()>0 && txtTitulo.getText().toString().length()>0){
            NotasDao notasDAO = new NotasDao(editor.this);
            nota.setTexto(txtNota.getText().toString());
            nota.setTitulo(txtTitulo.getText().toString());
            if(idNota!=-1){
                notasDAO.atualizarNota(nota);
            }
            else{
                notasDAO.inserirNota(nota);
            }
            finish();

        }
    }


    //Ao clicar no botão voltar, ele verifica se há texto para ser salvo.
    //Utiliza o mesmo método do botão salvar.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        salvarNota();
    }

    // Faz com que o botão voltar da toolbar funcione igual ao do celular
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}
