package com.example.e_pertanian.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_pertanian.Login;
import com.example.e_pertanian.R;
import com.example.e_pertanian.model.Schedule;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.SnapshotHolder;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

import static androidx.recyclerview.widget.RecyclerView.*;


public class ScheduleFragment extends Fragment  {

    FirebaseUser user;

    private FloatingActionButton tambahjdwl;
    DatabaseReference dbJadwal;
    private ArrayList<Schedule> scheduleArrayList;

    private RecyclerView jadwalRecylerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    ProgressBar progressBar;



    public class scheduleHolder extends RecyclerView.ViewHolder{

        TextView waktu;
        TextView jenisKegiatan, isAuto;
        CardView cvpjdl;
        public TextView lama;
        ImageButton ibMenu;

        public scheduleHolder(@NonNull View itemView) {
            super(itemView);
            lama =(TextView)itemView.findViewById(R.id.lamaKg);
            waktu = (TextView)itemView.findViewById(R.id.jamItem);
            jenisKegiatan = (TextView)itemView.findViewById(R.id.JenisKgtn);
            isAuto = (TextView)itemView.findViewById(R.id.tvOtomatis);
            cvpjdl = (CardView)itemView.findViewById(R.id.cardPjdl);
            ibMenu = (ImageButton)itemView.findViewById(R.id.ibMenu);
        }

        public void setLama(String string) {
            lama.setText(string);
        }

        public void setWaktu(String string) {
            waktu.setText(string);
        }

        public void setJenisKegiatan(Long id) {
            String[] jenisKG = getResources().getStringArray(R.array.daftar_kegiatan);
            jenisKegiatan.setText(jenisKG[id.intValue()]);
        }

        public void setIsAuto(boolean s) {
            if (s == true){
                isAuto.setVisibility(VISIBLE);
            }else {
                isAuto.setVisibility(INVISIBLE);
            }
        }
    }

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar)getActivity().findViewById(R.id.progressBar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish();
            return;
        }


        TextView tg, hr, bl, th;
        tg = (TextView)getActivity().findViewById(R.id.tanggal);
        hr = (TextView)getActivity().findViewById(R.id.hari);
        bl = (TextView)getActivity().findViewById(R.id.bulan);
        th = (TextView)getActivity().findViewById(R.id.tahun);

        DateFormat dateFm = new SimpleDateFormat("yyyy-MMM-dd-EEE");
        String hariIni = dateFm.format(Calendar.getInstance().getTime());
        String[] bagianTgl = hariIni.split("-");

        tg.setText(bagianTgl[2]);
        hr.setText(bagianTgl[3]);
        bl.setText(bagianTgl[1]);
        th.setText(bagianTgl[0]);


        jadwalRecylerView = (RecyclerView)getActivity().findViewById(R.id.rvBuatJadwal);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        jadwalRecylerView.setLayoutManager(linearLayoutManager);
        jadwalRecylerView.setHasFixedSize(true);
        dbJadwal = FirebaseDatabase.getInstance().getReference();
        fetch();

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
                //dbJadwal.child("jadwal").child(user.getUid()).child("tanggal");
                String pilihan = dateFm.format(date.getTime());
                String[] bagianTgl = pilihan.split("-");

                tg.setText(bagianTgl[2]);
                hr.setText(bagianTgl[3]);
                bl.setText(bagianTgl[1]);
                th.setText(bagianTgl[0]);

            }
        });


        tambahjdwl = (FloatingActionButton)getActivity().findViewById(R.id.btntambahJadwal);


        tambahjdwl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahJadwalFragment tambah = new TambahJadwalFragment();
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.replace(R.id.pager,tambah);
                ts.commit();
            }
        });

        dbJadwal.child("jadwal").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    progressBar.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance().getReference().child("jadwal").child(user.getUid());


        FirebaseRecyclerOptions<Schedule> options = new FirebaseRecyclerOptions.Builder<Schedule>().setQuery(query, new SnapshotParser<Schedule>() {
            @NonNull
            @Override
            public Schedule parseSnapshot(@NonNull DataSnapshot snapshot) {

                return new Schedule(snapshot.child("id").getValue().toString(),
                        (Long) snapshot.child("jenisKg").getValue(),
                        snapshot.child("tanggal").getValue().toString(),
                        snapshot.child("waktu").getValue().toString(),
                        snapshot.child("lama").getValue().toString(),
                        Boolean.parseBoolean(snapshot.child("auto").getValue().toString()));
            }
        }).build();



        adapter = new FirebaseRecyclerAdapter<Schedule, scheduleHolder>(options) {

            @NonNull
            @Override
            public scheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule,parent,false);

                return new scheduleHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull scheduleHolder holder, int position, @NonNull Schedule model) {
                progressBar.setVisibility(INVISIBLE);

                holder.setLama(model.getLama());
                holder.setWaktu(model.getWaktu());
                holder.setJenisKegiatan(model.getJenisKg());
                holder.setIsAuto(model.isAuto());

                holder.ibMenu.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(getActivity(),holder.ibMenu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.menuEdit:
                                        //Toast.makeText(getActivity(),String.valueOf(position), Toast.LENGTH_SHORT).show();

                                        UpdateScheduleFragment updateJadwal = new UpdateScheduleFragment();
                                        Bundle bundle = new Bundle();

                                        bundle.putString("id jadwal",model.getId());
                                        bundle.putString("waktu",model.getWaktu());
                                        bundle.putString("tanggal",model.getTanggal());
                                        bundle.putInt("jenis",model.getJenisKg().intValue());
                                        bundle.putString("lama",model.getLama());
                                        bundle.putBoolean("otomatis",model.isAuto());
                                        updateJadwal.setArguments(bundle);

                                        FragmentTransaction ts = getFragmentManager().beginTransaction();
                                        ts.replace(R.id.pager,updateJadwal);
                                        ts.commit();
                                        break;
                                    case R.id.menuHapus:
                                        DatabaseReference db = dbJadwal.child("jadwal").child(user.getUid()).child(model.getId());
                                        db.removeValue();
                                        Toast.makeText(getActivity(), "Delete data...", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                return false;
                            }
                        });
                        popupMenu.inflate(R.menu.menu);
                        popupMenu.show();
                    }
                });
            }
        };
        jadwalRecylerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vii = inflater.inflate(R.layout.fragment_schedule, container, false);
        return vii;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }


}