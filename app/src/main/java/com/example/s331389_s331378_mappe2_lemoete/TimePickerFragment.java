package com.example.s331389_s331378_mappe2_lemoete;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);
        int minutt = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(), time,minutt,true);

        return timePickerDialog;
    }
}
