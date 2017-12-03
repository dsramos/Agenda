package com.example.davison.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.davison.agenda.converter.AlunoConverter;
import com.example.davison.agenda.dao.Dao;
import com.example.davison.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by Davison on 10/11/2017.
 */

public class EnviaAlunoTask extends AsyncTask<Void,Object,String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunoTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        Dao dao = new Dao(context);
        List<Aluno> alunos = dao.buscaAlunos();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,"Aguarde","Enviando aluno ...",true,true);

    }

    @Override
    protected void onPostExecute(String resposta) {
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
}
