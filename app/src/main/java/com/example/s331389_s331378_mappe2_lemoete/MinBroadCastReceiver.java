package com.example.s331389_s331378_mappe2_lemoete;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MinBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //starter periodisk melding/notifikasjon
       Intent i = new Intent(context, SettPeriodiskService.class);
       context.startService(i);
    }
}
