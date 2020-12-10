package com.example.e_pertanian.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.e_pertanian.Login;
import com.example.e_pertanian.MainActivity;
import com.example.e_pertanian.R;
import com.example.e_pertanian.model.Schedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TambahJadwalFragment extends Fragment implements View.OnClickListener {

    Calendar kalender;
    private Spinner jensiKegiatan;
    private EditText ettgl;
    private EditText etWkt;
    private EditText etLm;
    private CheckBox isOto;

    private Schedule jadwal;

    private Button simpan, batal;

    DatabaseReference mDatabase;

    FirebaseUser user;

    public TambahJadwalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        jadwal = new Schedule();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish();
            return;
        }

        kalender = Calendar.getInstance();
        ettgl = (EditText)getActivity().findViewById(R.id.etTW);
        etWkt = (EditText)getActivity().findViewById((R.id.etWaktu));
        etLm = (EditText)getActivity().findViewById(R.id.etLama);
        jensiKegiatan = (Spinner)getActivity().findViewById(R.id.spJK);
        isOto = (CheckBox)getActivity().findViewById(R.id.cbIsAuto);
        simpan = (Button)getActivity().findViewById(R.id.btnTambah);
        batal = (Button)getActivity().findViewById(R.id.btnBatal);
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleFragment jadwal = new ScheduleFragment();
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.replace(R.id.pager,jadwal);
                ts.commit();
            }
        });

        ettgl.setOnClickListener(this);
        etWkt.setOnClickListener(this);
        simpan.setOnClickListener(this);

        isOto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOto.isChecked()){
                    Toast.makeText(getActivity(), "Otomatis", Toast.LENGTH_SHORT).show();
                }
            }
        });

        jensiKegiatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String isi = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), isi, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTambah:
                savejadwal();
                break;
            case R.id.etTW:
                new DatePickerDialog((Activity) getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        kalender.set(Calendar.YEAR, year);
                        kalender.set(Calendar.MONTH, month);
                        kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateTanggal();
                    }
                }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.etWaktu:
                new TimePickerDialog((Activity) getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        kalender.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        kalender.set(Calendar.MINUTE, minute);
                        updateWaktu();
                    }
                }, kalender.get(Calendar.HOUR_OF_DAY), kalender.get(Calendar.MINUTE), true).show();
                break;
            case R.id.etLama:
                break;
            default:
        }
    }

    private void savejadwal(){
        String jenisK =  jensiKegiatan.getSelectedItem().toString().trim();
        String tanggal = ettgl.getText().toString().trim();
        String waktu = etWkt.getText().toString().trim();
        String lama = etLm.getText().toString().trim();
        boolean isAuto = isOto.isChecked();

        boolean isEmptyFields = false;


        if (TextUtils.isEmpty(tanggal)){
            isEmptyFields = true;
            ettgl.setError("Field ini tidak boleh kosong");
        }


        if (TextUtils.isEmpty(waktu)){
            isEmptyFields = true;
            etWkt.setError("Ini tidak boleh kosong");
        }


        if (TextUtils.isEmpty(lama)){
            isEmptyFields = true;
            etLm.setError("Ini tidak boleh kosong");
        }

        /*
        if (TextUtils.isEmpty(String.valueOf(jenisK))){
            isEmptyFields = true;
            TextView errorText = (TextView)jensiKegiatan.getSelectedView();
            errorText.setError("Ini tidak boleh kosong");
        }

         */

        if (!isEmptyFields){
            DatabaseReference db = mDatabase.child("jadwal");
            Toast.makeText(getActivity(), "Jadwal tersimpan", Toast.LENGTH_SHORT).show();

            String id = db.push().getKey();
            jadwal.setId(id);
            jadwal.setTanggal(tanggal);
            jadwal.setWaktu(waktu);
            jadwal.setAuto(isAuto);
            jadwal.setJenisKg(jenisK);
            jadwal.setLama(lama);
            jadwal.setAuto(isAuto);


            db.child(user.getUid()).child(id).setValue(jadwal);

            ScheduleFragment jadwal = new ScheduleFragment();
            FragmentTransaction ts = getFragmentManager().beginTransaction();
            ts.replace(R.id.pager,jadwal);
            ts.commit();
        }else {
            Toast.makeText(getActivity(), "tidak tersimpan", Toast.LENGTH_SHORT).show();
        }
    }
}