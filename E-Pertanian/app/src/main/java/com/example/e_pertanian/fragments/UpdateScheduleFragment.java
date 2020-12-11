package com.example.e_pertanian.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.e_pertanian.R;
import com.example.e_pertanian.model.Schedule;


public class UpdateScheduleFragment extends Fragment {

    EditText etTaggal, etWaktu, etLama;
    Spinner spJeniskegiatan;
    CheckBox dbOtomatis;
    Button batal,edit;

    Schedule jadwal;

    public UpdateScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jadwal = new Schedule();

        etWaktu = (EditText)getActivity().findViewById(R.id.etWaktu);
        etTaggal = (EditText)getActivity().findViewById(R.id.etTW);
        spJeniskegiatan = (Spinner)getActivity().findViewById(R.id.spJK);
        etLama = (EditText)getActivity().findViewById(R.id.etLama);
        dbOtomatis = (CheckBox)getActivity().findViewById(R.id.cbIsAuto);
        edit = (Button)getActivity().findViewById(R.id.btnUbah);
        batal = (Button)getActivity().findViewById(R.id.btnBatal);

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            etWaktu.setText(bundle.getString("waktu"));
            etTaggal.setText(bundle.getString("tanggal"));
            spJeniskegiatan.setSelection(bundle.getInt("jenis"));
            etLama.setText(bundle.getString("lama"));
            dbOtomatis.setChecked(bundle.getBoolean("otomatis"));

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_schedule, container, false);
    }
}