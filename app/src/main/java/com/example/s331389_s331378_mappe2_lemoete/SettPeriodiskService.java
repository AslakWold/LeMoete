package com.example.s331389_s331378_mappe2_lemoete;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.util.Calendar;


public class SettPeriodiskService extends Service {
    String tidspunkt;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Bruker alarmManager til å sende ut tiden til gitt klokkeslett.

        getTidspunkt();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        //c.add(Calendar.DAY_OF_YEAR,1);
        c.set(Calendar.HOUR_OF_DAY,getTime(tidspunkt));
        c.set(Calendar.MINUTE,getMinutt(tidspunkt));
        c.set(Calendar.SECOND,0);
        Intent i = new Intent(this, MinService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,i, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);



        //Hindrer den ved å sende ved startup
        if(System.currentTimeMillis()+50 > c.getTimeInMillis()){
            c.add(Calendar.DAY_OF_YEAR,1);
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),60*1000*60*24,pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    public void getTidspunkt(){
        tidspunkt  = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getString("tidspunkt","7:0");
    }


    //Hjelpemetoder for å finne time og minutter det skal sendes på
    public int getTime(String tidspunkt){
        String [] arr = tidspunkt.split(":",2);
        return Integer.parseInt(arr[0]);
    }
    public int getMinutt(String tidspunkt){
        String [] arr = tidspunkt.split(":",2);
        return Integer.parseInt(arr[1]);
    }
}
