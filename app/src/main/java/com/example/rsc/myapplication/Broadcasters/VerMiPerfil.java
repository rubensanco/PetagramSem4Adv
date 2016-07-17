package com.example.rsc.myapplication.Broadcasters;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.rsc.myapplication.activities.MainActivity;


public class VerMiPerfil extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String ACTION_KEY = "VER_MI_PERFIL";
        String accion = intent.getAction();

        if (ACTION_KEY.equals(accion)){

            Intent i = new Intent(context,MainActivity.class);
            i.putExtra("tab", 1);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        }
    }
}
