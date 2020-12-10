package com.example.e_pertanian.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_pertanian.Login;
import com.example.e_pertanian.MainActivity;
import com.example.e_pertanian.R;
import com.example.e_pertanian.schedule.SchedulingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class ScheduleFragment extends Fragment  {

    private String tglW;
    private TextView tglwaktu;
    private FloatingActionButton tambahjdwl;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish();
            return;
        }


        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder((Activity) getContext(), R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

            }
        });

        tambahjdwl = (FloatingActionButton)getActivity().findViewById(R.id.btntambahJadwal);
        tglwaktu = (TextView)getActivity().findViewById(R.id.tangalWaktu);


        tambahjdwl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahJadwalFragment tambah = new TambahJadwalFragment();
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.replace(R.id.pager,tambah);
                ts.commit();
            }
        });
        tglW = DateFormat.getDateTimeInstance().format(new Date());
        tglwaktu.setText(tglW);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vii = inflater.inflate(R.layout.fragment_schedule, container, false);
        return vii;
    }


}