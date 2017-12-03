package com.example.davison.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.davison.agenda.FormularioActivity;
import com.example.davison.agenda.R;
import com.example.davison.agenda.dao.Dao;

/**
 * Created by Davison on 10/11/2017.
 */

public class SmsReceiver extends BroadcastReceiver{

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage sms = SmsMessage.createFromPdu(pdu,formato);

        String telefone = sms.getDisplayOriginatingAddress();
        Dao dao = new Dao(context);
        if(dao.eAluno(telefone)){
            Toast.makeText(context,"Chegou um SMS",Toast.LENGTH_SHORT).show();
            MediaPlayer media = MediaPlayer.create(context, R.raw.msg);
            media.start();
        }
        dao.close();

    }
}
