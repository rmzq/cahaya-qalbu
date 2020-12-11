package com.example.e_pertanian.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.e_pertanian.R;
import com.example.e_pertanian.model.Schedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class UpdateScheduleFragment extends Fragment {

    EditText etTaggal, etWaktu, etLama;
    Spinner spJeniskegiatan;
    CheckBox cbOtomatis;
    Button batal,edit;

    Schedule jadwal;
    Calendar kalender;
    Bundle bundle;

    DatabaseReference dbJadwal;
    FirebaseUser user;

    public UpdateScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jadwal = new Schedule();
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbJadwal = FirebaseDatabase.getInstance().getReference();

        etWaktu = (EditText)getActivity().findViewById(R.id.etWaktu);
        etTaggal = (EditText)getActivity().findViewById(R.id.etTW);
        spJeniskegiatan = (Spinner)getActivity().findViewById(R.id.spJK);
        etLama = (EditText)getActivity().findViewById(R.id.etLama);
        cbOtomatis = (CheckBox)getActivity().findViewById(R.id.cbIsAuto);
        edit = (Button)getActivity().findViewById(R.id.btnUbah);
        batal = (Button)getActivity().findViewById(R.id.btnBatal);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleFragment jadwal = new ScheduleFragment();
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.replace(R.id.pager,jadwal);
                ts.commit();
            }
        });

        bundle = this.getArguments();
        if (bundle!=null){
            etWaktu.setText(bundle.getString("waktu"));
            etTaggal.setText(bundle.getString("tanggal"));
            spJeniskegiatan.setSelection(bundle.getInt("jenis"));
            etLama.setText(bundle.getString("lama"));
            cbOtomatis.setChecked(bundle.getBoolean("otomatis"));
        }
        
        //buat edit isi
        kalender = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, month);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTanggal();
            }
        };

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                kalender.set(Calendar.HOUR_OF_DAY,hourOfDay);
                kalender.set(Calendar.MINUTE, minute);
                updateWaktu();
            }
        };

        etTaggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog((Activity)getContext(), date, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog((Activity) getContext(),time, kalender.get(Calendar.HOUR_OF_DAY), kalender.get(Calendar.MINUTE), true).show();
            }
        });

    }

    private void updateWaktu() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etWaktu.setText(sdf.format(kalender.getTime()));
    }

    private void updateTanggal() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etTaggal.setText(sdf.format(kalender.getTime()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_schedule, container, false);
    }

    public void updateData(){
        Long jenisK =  spJeniskegiatan.getSelectedItemId();
        String tanggal = etTaggal.getText().toString().trim();
        String waktu = etWaktu.getText().toString().trim();
        String lama = etLama.getText().toString().trim();
        boolean isAuto = cbOtomatis.isChecked();

        boolean isEmptyFields = false;


        if (TextUtils.isEmpty(tanggal)){
            isEmptyFields = true;
            etTaggal.setError("Field ini tidak boleh kosong");
        }


        if (TextUtils.isEmpty(waktu)){
            isEmptyFields = true;
            etWaktu.setError("Ini tidak boleh kosong");
        }


        if (TextUtils.isEmpty(lama)){
            isEmptyFields = true;
            etLama.setError("Ini tidak boleh kosong");
        }

        /*
        if (TextUtils.isEmpty(String.valueOf(jenisK))){
            isEmptyFields = true;
            TextView errorText = (TextView)jensiKegiatan.getSelectedView();
            errorText.setError("Ini tidak boleh kosong");
        }

         */

        if (!isEmptyFields){
            DatabaseReference db = dbJadwal.child("jadwal");
            Toast.makeText(getActivity(), "Jadwal terupdate", Toast.LENGTH_SHORT).show();

            jadwal.setId(bundle.getString("id jadwal"));
            jadwal.setTanggal(tanggal);
            jadwal.setWaktu(waktu);
            jadwal.setAuto(isAuto);
            jadwal.setJenisKg(jenisK);
            jadwal.setLama(lama);
            jadwal.setAuto(isAuto);


            db.child(user.getUid()).child(bundle.getString("id jadwal")).setValue(jadwal);

            ScheduleFragment jadwal = new ScheduleFragment();
            FragmentTransaction ts = getFragmentManager().beginTransaction();
            ts.replace(R.id.pager,jadwal);
            ts.commit();
        }else {
            Toast.makeText(getActivity(), "tidak tersimpan", Toast.LENGTH_SHORT).show();
        }

    }
}