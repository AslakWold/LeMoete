package com.example.s331389_s331378_mappe2_lemoete;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


//Timepickerfragment klasse for å bruke i fragments.
public class TimePickerFragment2 extends DialogFragment {
    TimePickerDialog.OnTimeSetListener setListener;
    //Fragments need to empty constructor
    TimePickerFragment2(){ }

    TimePickerFragment2(TimePickerDialog.OnTimeSetListener setListener){
        this.setListener = setListener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int timer = c.get(Calendar.HOUR_OF_DAY);
        int minutter = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, setListener,timer,minutter, true);
    }
}
