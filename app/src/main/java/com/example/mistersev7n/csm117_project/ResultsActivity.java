package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    String sessCode = CreateSessionActivity.sessionCode;
    String rootChild = profEnterSession.sessionCodeTrue;
    String quizName = QuizList.list_quizName;

    // Write a message to the database
    DatabaseReference database;

    DatabaseReference ARef;
    DatabaseReference BRef;
    DatabaseReference CRef;
    DatabaseReference DRef;

    int value1;
    int value2;
    int value3;
    int value4;

    String value;
    ArrayList<BarEntry> barEntries = new ArrayList<>();


    BarChart barChart;
    private static final String TAG = "ResultsActivity";

    //****************************************
    //READS MULTIPLE CHOICE RESULTS FROM DATABASE
    //****************************************

    public String quizChildName = QuizzActivity.quizChildName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if (sessCode == null || sessCode.isEmpty()) {
            sessCode = rootChild;
        }

        // Write a message to the database
        database = FirebaseDatabase.getInstance().getReference().child(sessCode);

        ARef = database.child("Quiz").child(quizName).child("countA");
        BRef = database.child("Quiz").child(quizName).child("countB");
        CRef = database.child("Quiz").child(quizName).child("countC");
        DRef = database.child("Quiz").child(quizName).child("countD");

        ARef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                value1 = (int) count;
                //value = dataSnapshot.getValue(String.class);
                //value1 = Integer.valueOf(value);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        BRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //value = dataSnapshot.getValue(String.class);
                //value2 = Integer.valueOf(value);

                long count = (long) dataSnapshot.getValue();
                value2 = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        CRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //value = dataSnapshot.getValue(String.class);
                //value3 = Integer.valueOf(value);

                long count = (long) dataSnapshot.getValue();
                value3 = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        DRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //value = dataSnapshot.getValue(String.class);
                //value4 = Integer.valueOf(value);

                long count = (long) dataSnapshot.getValue();
                value4 = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        Button refresh = findViewById(R.id.refreshButt);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshButtonClick();
            }
        });

    }


    public void refreshButtonClick() {
        // Read from the database

        ARef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                value1 = (int) count;
                //value = dataSnapshot.getValue(String.class);
                //value1 = Integer.valueOf(value);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        BRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //value = dataSnapshot.getValue(String.class);
                //value2 = Integer.valueOf(value);

                long count = (long) dataSnapshot.getValue();
                value2 = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        CRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //value = dataSnapshot.getValue(String.class);
                //value3 = Integer.valueOf(value);

                long count = (long) dataSnapshot.getValue();
                value3 = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        DRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //value = dataSnapshot.getValue(String.class);
                //value4 = Integer.valueOf(value);

                long count = (long) dataSnapshot.getValue();
                value4 = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //****************************************
        //BARCHART SPECIFICATIONS
        //****************************************

        barChart = (BarChart) findViewById(R.id.bargraph);
        TextView myTextview = (TextView) findViewById(R.id.resultstextView);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.getXAxis().setDrawLabels(false);
        barChart.getDescription().setEnabled(false);


        barEntries.add(new BarEntry(1, value1));
        barEntries.add(new BarEntry(2, value2));
        barEntries.add(new BarEntry(3, value3));
        barEntries.add(new BarEntry(4, value4));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Student Feedback");
        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS[0], ColorTemplate.VORDIPLOM_COLORS[1], ColorTemplate.VORDIPLOM_COLORS[2],ColorTemplate.VORDIPLOM_COLORS[3]);


        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);
        barChart.setData(data);

        //****************************************
        //SETS MESSAGE
        //****************************************

        if (value1 > value2 && value1 > value3 && value1 > value4) {
            myTextview.setText("Most students chose A");
        } else if (value2 > value1 && value2 > value3 && value2 > value4) {
            myTextview.setText("Most students chose B");
        } else if (value3 > value2 && value3 > value1 && value3 > value4) {
            myTextview.setText("Most students chose C");
        } else if (value1 == 0 && value2 == 0 && value3 == 0 && value4 == 0 ) {
            myTextview.setText("No responses yet.");
        }
        else {
            myTextview.setText("Most students chose D");
        }
    }

    //************************************************************
    // MENU
    //************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.opt_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.quizz:
                Intent intent = new Intent(this, QuizProfActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(this, MainActivity.class);
                this.startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
