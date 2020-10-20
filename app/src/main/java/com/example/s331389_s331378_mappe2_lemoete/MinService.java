package com.example.s331389_s331378_mappe2_lemoete;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MinService extends Service {

    String melding;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DBHandler db = new DBHandler(this);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        //Ingenting å si hvilket klokkeslett her
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,3);
        c.set(Calendar.SECOND,2);
        Format format = new SimpleDateFormat("MM/dd/yy");
        String date = format.format(new Date());

        if(!db.hentMoeterIdag(date).isEmpty()){
            //Lager notifikasjon
            getMelding();
            NotificationManager notMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent i = new Intent(Intent.ACTION_VIEW); //Sender til sms applikasjonen ved trykk på notifikasjon
            //i.addCategory(Intent.CATEGORY_DEFAULT);
            //i.setType("vnd.android-dir/mms-sms");
            i.setData(Uri.parse("sms:")); //Finner sms
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,0);
            Notification not = new NotificationCompat.Builder(this)
                    .setContentTitle(("Le Moete"))
                    .setContentText(melding + "\n" + getResources().getString(R.string.notMeldingExtra))
                    .setSmallIcon(R.mipmap.lemoetelogo)
                    .setContentIntent(pendingIntent).build();
            not.flags |= Notification.FLAG_AUTO_CANCEL;
            notMan.notify(0, not);
            sendMeldinger();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //Metode som finner ut hvilke kontakter som har møte idag og sender ut melding
    public void sendMeldinger(){
        DBHandler db  = new DBHandler(this);

        //Henter tillatelser
        int MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int MY_PHONE_STATE_PERMISSION = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        //Ingenting å si hvilket klokkeslett her
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,3);
        c.set(Calendar.SECOND,2);
        Format format = new SimpleDateFormat("MM/dd/yy");
        String date = format.format(new Date());
        List<Møte> moeterIdag = db.hentMoeterIdag(date);
        List<Kontakt> konakter = new ArrayList<>();
        List<String> telefonnummere = new ArrayList<>();

        //Lagrer kontakter som har møte og hindrer at det blir lagt til duplikater
        for(Møte møte : moeterIdag){
            List<Kontakt> tmpKontakter;
            tmpKontakter = db.finnDeltagere(møte.getMoete_ID());
            for(Kontakt kontakt : tmpKontakter){
                boolean exists = false;
                for(Kontakt eksisterendeKontakter : konakter){
                    if(kontakt.getBrukernavn().equals(eksisterendeKontakter.getBrukernavn())){
                        exists = true;
                    }
                }
                if(!exists){
                    konakter.add(kontakt);
                }
            }
        }

        //Fyller telefonnummere array.
        for(Kontakt kontakt : konakter){
            telefonnummere.add(kontakt.getTelefon());
            System.out.println(kontakt.toString());
        }

        //Sender melding
        if(MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED &&
                MY_PHONE_STATE_PERMISSION == PackageManager.PERMISSION_GRANTED) {
            for (String nr : telefonnummere) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(nr, null, melding, null, null);
            }
        }else{

            //Spør om tillatelse i Mainaactivity sin onCreate Istedenfor.
            //ActivityCompat.requestPermissions(, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }
    //SHAREDPREFERENCE metode for melding som skal skrives i notifikasjon og melding
    public void getMelding(){
        melding  = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getString("melding","Husk møte idag 4");
    }

}
