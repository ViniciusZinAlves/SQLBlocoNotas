package com.example.blocodenotas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper
{

    private static String nome_banco = "Banco";
    private static  int versao_banco=1;

    public Conexao(Context context)
    {
        super(context,nome_banco,null,versao_banco);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase)
    {
        dataBase.execSQL("create table notas(id integer primary key,titulo varchar(50),texto varchar(500))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int iOldVersion, int iNewVersion) {

    }
}
