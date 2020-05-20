package com.example.blocodenotas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    ArrayList<Nota> notas = new ArrayList<Nota>();
    NotaAdapter arrayNota;
    ListView lista_notas;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista_notas = (ListView)findViewById(R.id.listanotas);
        atualizarNotas();

        lista_notas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,editor.class);
                intent.putExtra("notaId",notas.get(position).getId());
                //Inicia a activity do editor
                startActivity(intent);
            }
        });

        lista_notas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja mesmo excluir?")
                        .setNegativeButton("Não",null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                excluirNota(notas.get(position));
                            }
                        });
                adb.show();
                return true;
            }
        });

        btnAdd = (Button)findViewById(R.id.btnadicionar);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,editor.class);
                startActivity(intent);
            }
        });

    }


    public void excluirNota(Nota nota)
    {
        NotasDao notasDAO = new NotasDao(MainActivity.this);
        notasDAO.excluirNota(nota);
        atualizarNotas();
    }


    public void atualizarNotas()
    {
        NotasDao NotasDao = new NotasDao(MainActivity.this);

        if(NotasDao.obterNotas()!=null){
            notas = new ArrayList<Nota>(NotasDao.obterNotas());
        }

        arrayNota = new NotaAdapter(this, notas);
        lista_notas.setAdapter(arrayNota);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        atualizarNotas();
    }

}
