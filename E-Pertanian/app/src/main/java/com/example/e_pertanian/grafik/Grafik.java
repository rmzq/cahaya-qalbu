package com.example.e_pertanian.grafik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_pertanian.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Grafik extends AppCompatActivity {

    EditText xValue, yValue;
    Button insertBtn;
    LineChart lineChart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    LineDataSet lineDataSet = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        xValue = findViewById(R.id.editTextX);
        yValue = findViewById(R.id.editTextY);
        insertBtn = findViewById(R.id.btnInsert);
        lineChart = findViewById(R.id.Linechart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("ChartValues");
        insertData();

    }

    private void insertData() {
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = myRef.push().getKey();
                int x = Integer.parseInt(xValue.getText().toString());
                int y = Integer.parseInt(yValue.getText().toString());

                DataPoint dataPoint = new DataPoint(x, y);
                myRef.child(id).setValue(dataPoint);

                retrieveData();
            }
        });
    }

    private void retrieveData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> dataVals = new ArrayList<Entry>();

                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                        DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
                        dataVals.add(new Entry(dataPoint.getxValue(), dataPoint.getyValue()));
                    }

                    showChart(dataVals);
                } else {
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showChart(ArrayList<Entry> dataVals) {
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("Hasil Panen");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}