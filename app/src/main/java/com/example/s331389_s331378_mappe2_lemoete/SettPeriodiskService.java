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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Bruker alarmManager til å sende ut tiden til gitt klokkeslett.
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,11);
        c.set(Calendar.SECOND,5);
        Intent i = new Intent(this, MinService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,i, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),60*1000*60*24,pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

}
