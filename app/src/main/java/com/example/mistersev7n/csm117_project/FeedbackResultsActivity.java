package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import android.widget.Button;
import android.view.View;
import android.content.Intent;

import java.util.ArrayList;

public class FeedbackResultsActivity extends AppCompatActivity {

    String sessCode = CreateSessionActivity.sessionCode;
    String rootChild = profEnterSession.sessionCodeTrue;

    // Write a message to the database
    DatabaseReference database;

    DatabaseReference fastRef;
    DatabaseReference goodRef;
    DatabaseReference slowRef;

    TextView message;

    int numberGood;
    int numberSlow;
    int numberFast;

    String value;
    //ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<PieEntry> pieEntries = new ArrayList<>();


    // BarChart barChart;
    PieChart pieChart;
    private static final String TAG = "FeedbackResultsActivity";

    //****************************************
    //READS MULTIPLE CHOICE RESULTS FROM DATABASE
    //****************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_results);

        message = (TextView) findViewById(R.id.messageFeedback);

        if (sessCode == null || sessCode.isEmpty()) {
            sessCode = rootChild;
        }

        // Write a message to the database
        database = FirebaseDatabase.getInstance().getReference().child(sessCode);

        fastRef = database.child("Feedback").child("fast");
        goodRef = database.child("Feedback").child("good");
        slowRef = database.child("Feedback").child("slow");


        goodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                numberGood = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        slowRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                numberSlow = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        fastRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                numberFast = (int) count;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        Button refresh = findViewById(R.id.refreshButt);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               refreshButtonClick(v);
            }
        });

    }

    public void refreshButtonClick(View v) {
        // Read from the database

        goodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                numberGood = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        slowRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                numberSlow = (int) count;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        fastRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                numberFast = (int) count;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        //****************************************
        //PIECHART SPECIFICATIONS
        //****************************************



        pieChart = (PieChart) findViewById(R.id.chart);

        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(false);
        pieChart.setRotationEnabled(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setCenterTextSize(12);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.animateXY(1400, 1400);
        pieChart.setCenterText("Student Feedback");
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawEntryLabels(false);


        pieEntries.clear();

        pieEntries.add(new PieEntry(numberGood, "Good"));
        pieEntries.add(new PieEntry(numberSlow, "Too Slow"));
        pieEntries.add(new PieEntry(numberFast, "Too Fast"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, " ");
        //pieDataSet.setColors(new int[] {Color.GREEN, Color.YELLOW, Color.RED});

        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS[0], ColorTemplate.VORDIPLOM_COLORS[1], ColorTemplate.VORDIPLOM_COLORS[4]);

        PieData data = new PieData(pieDataSet);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setData(data);

        //****************************************
        //SETS MESSAGE
        //****************************************


        if (numberGood > numberSlow + numberFast) {
            message.setText("Most students are understanding, good job!");
        } else if (numberSlow > numberGood && numberSlow > numberFast) {
            message.setText("Most students think the teaching is too slow!");
        } else if (numberFast > numberGood && numberFast > numberSlow) {
            message.setText("Most students think the teaching is too fast!");
        } else if (numberFast ==0 && numberSlow ==0 && numberGood ==0) {
            message.setText("No responses yet.");
        }
        else {
            message.setText("Looks like feedback is mixed, it might be a good time to give an example problem?");
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
                Intent intent = new Intent(this, QuizList.class);
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
