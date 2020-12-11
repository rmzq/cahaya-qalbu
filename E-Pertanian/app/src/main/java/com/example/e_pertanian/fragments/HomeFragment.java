package com.example.e_pertanian.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.e_pertanian.Login;
import com.example.e_pertanian.MainActivity;
import com.example.e_pertanian.R;
import com.example.e_pertanian.model.Schedule;
import com.example.e_pertanian.schedule.SchedulingActivity;
import com.example.e_pertanian.watering.Watering;
import com.google.firebase.auth.FirebaseAuth;


public class HomeFragment extends Fragment {

    Button btnLogout;
    FrameLayout schedule, watering,irigasi;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogout = (Button) getActivity().findViewById(R.id.btnLogout);
        schedule = (FrameLayout) getActivity().findViewById(R.id.scheduling);
        watering = (FrameLayout) getActivity().findViewById(R.id.watering);
        irigasi = (FrameLayout)getActivity().findViewById(R.id.irigasi);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(getActivity(), Login.class);
                getActivity().finish();
                startActivity(intToMain);

            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleFragment tambah = new ScheduleFragment();
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.replace(R.id.pager,tambah);
                ts.commit();
            }
        });

        watering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMain = new Intent(getActivity(), Watering.class);
                startActivity(intToMain);
            }
        });

        irigasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMain = new Intent(getActivity(), Watering.class);
                startActivity(intToMain);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}