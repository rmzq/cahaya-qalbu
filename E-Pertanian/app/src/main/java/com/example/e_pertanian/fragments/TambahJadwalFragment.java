package com.example.e_pertanian.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.e_pertanian.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahJadwalFragment extends Fragment {

    Calendar kalender;
    private EditText ettgl;
    private EditText etWkt;
    private EditText etLm;

    public TambahJadwalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kalender = Calendar.getInstance();
        ettgl = (EditText)getActivity().findViewById(R.id.etTW);
        etWkt = (EditText)getActivity().findViewById((R.id.etWaktu));
        etLm = (EditText)getActivity().findViewById(R.id.etLama);


        TimePickerDialog.OnTimeSetListener waktu = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                kalender.set(Calendar.HOUR_OF_DAY,hourOfDay);
                kalender.set(Calendar.MINUTE, minute);
                updateWaktu();
            }
        };

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, month);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTanggal();
            }
        };


        ettgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog((Activity)getContext(), date, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etWkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog((Activity)getContext(), waktu, kalender.get(Calendar.HOUR_OF_DAY), kalender.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void updateWaktu() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etWkt.setText(sdf.format(kalender.getTime()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tambah_jadwal, container, false);
    }

    private void updateTanggal() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ettgl.setText(sdf.format(kalender.getTime()));
    }
}