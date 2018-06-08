package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private DatabaseReference fastRef;
    private DatabaseReference goodRef;
    private DatabaseReference slowRef;


    private Button fastButton;
    private Button slowButton;
    private Button goodButton;

    String rootChild = SessionActivity.sessionCodeTrue;

    //String sessCode = CreateSessionActivity.sessionCode;


    TextView textUpdate;

    private int countSlow;
    private int countGood;
    private int countFast;

    private static final String TAG = "FeedbackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        /*
        database = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/");
        final DatabaseReference myRef = database.getReference("Teachers").child("Feedback");
        myRef.child("slow").setValue(0);
        myRef.child("good").setValue(0);
        myRef.child("fast").setValue(0);
        */

        mDatabase = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/").getReference();
        fastRef = mDatabase.child(rootChild).child("Feedback").child("fast");
        goodRef = mDatabase.child(rootChild).child("Feedback").child("good");
        slowRef = mDatabase.child(rootChild).child("Feedback").child("slow");

        fastButton = findViewById(R.id.buttonFast);
        slowButton = findViewById(R.id.buttonSlow);
        goodButton = findViewById(R.id.buttonGood);

        textUpdate = (TextView) findViewById(R.id.testQ);

    //**********************
    // INITIALIZE COUNTERS

        //fastRef.setValue(0);
        //goodRef.setValue(0);
        //slowRef.setValue(0);


        fastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastButtonClick(v);
            }
        });

        goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodButtonClick(v);
            }
        });

        slowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slowButtonClick(v);
            }
        });


    //****************************************
    //SHOWS COUNTER FOR FAST BUTTON
    //****************************************
/*
        //fastRef.setValue(0);
        //Update counter each time we click on "TOO FAST" button
        fastRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long count = (long) dataSnapshot.getValue();
                fastRef.setValue(count);
                String val = Long.toString(count);
                //textUpdate.setText(val);
                textUpdate.setText(rootChild);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textUpdate.setText("Err");
            }
        });


        //Update counter each time we click on "GOOD" button
        goodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long count = (long) dataSnapshot.getValue();
                String val = Long.toString(count);
                textUpdate.setText(val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textUpdate.setText("Err");
            }
        });


        //Update counter each time we click on "TOO SLOW" button
        slowRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long count = (long) dataSnapshot.getValue();
                String val = Long.toString(count);
                textUpdate.setText(val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textUpdate.setText("Err");
            }
        });

*/

    }


    //Go to Timer screen
    public void timerScreen() {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
        finish();
    }

    /** Called when the user taps the "TOO SLOW" button */
    public void slowButtonClick(View view) {
        // Update database
        slowRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() != null) {
                    long value = (long) mutableData.getValue();
                    value++;
                    mutableData.setValue(value);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "likeTransaction:onComplete:" + databaseError);
            }
        });

        timerScreen();
    }

    /** Called when the user taps the "GOOD" button */
    public void goodButtonClick(View view) {
        // Update database
        goodRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() != null) {
                    long value = (long) mutableData.getValue();
                    value++;
                    mutableData.setValue(value);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "likeTransaction:onComplete:" + databaseError);
            }
        });


       timerScreen();
    }

    /** Called when the user taps the "TOO FAST" button */
    public void fastButtonClick(View view) {
        // Update database
/*
        fastRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long count = (long) dataSnapshot.getValue();
                count++;
                fastRef.setValue(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textUpdate.setText("Err");
            }
        });
*/

        fastRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() != null) {
                    long value = (long) mutableData.getValue();
                    value++;
                    mutableData.setValue(value);

                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "likeTransaction:onComplete:" + databaseError);
            }
        });

     timerScreen();
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
                Intent intent1 = new Intent(this, Student_Quizlist.class);
                this.startActivity(intent1);
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
