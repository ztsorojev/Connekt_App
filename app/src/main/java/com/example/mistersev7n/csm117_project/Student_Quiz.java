package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;




public class Student_Quiz extends AppCompatActivity {
    private static final String TAG = "QuizActivity";

    String rootChild = SessionActivity.sessionCodeTrue;
    String student_quizid = Student_Quizlist.student_quizId;

    TextView errorText;

    String quizNameChild = Student_Quizlist.list_quizName;

    int quiz_id_count;
    String quiz_id;
    boolean start;
    String chosen;
    String s_initialize;
    String increment_id;
    String s1, s2, s3, s4;

    DatabaseReference refQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__quiz);
        //this code is for initializing the text to see the data from firebase

        //show menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        errorText = findViewById(R.id.errorMsg);
        errorText.setVisibility(View.GONE);


        final TextView question_Text = findViewById(R.id.quiz_question);
        /*
        final TextView view_answer1 = findViewById(R.id.answer1);
        final TextView view_answer2 = findViewById(R.id.answer2);
        final TextView view_answer3 = findViewById(R.id.answer3);
        final TextView view_answer4 = findViewById(R.id.answer4);
        */

        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/");




        final DatabaseReference data_question, data_answer1, data_answer2, data_answer3, data_answer4, propA, propB, propC, propD;

        refQuiz = database.getReference(rootChild).child("Quiz").child(student_quizid);

        data_question = refQuiz.child("question");
        data_answer1 = refQuiz.child("countA");
        data_answer2 = refQuiz.child("countB");
        data_answer3 = refQuiz.child("countC");
        data_answer4 = refQuiz.child("countD");


        propA = refQuiz.child("a");
        propB = refQuiz.child("b");
        propC = refQuiz.child("c");
        propD = refQuiz.child("d");



        final ToggleButton tbA = findViewById(R.id.toggleButtonA);
        final ToggleButton tbB = findViewById(R.id.toggleButtonB);
        final ToggleButton tbC = findViewById(R.id.toggleButtonC);
        final ToggleButton tbD = findViewById(R.id.toggleButtonD);
        final Button submitButton = findViewById(R.id.submit_button);
        final Button back_list = findViewById(R.id.backtolist);
        final String quiz_number = quiz_id;

        data_question.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String questionText = dataSnapshot.getValue(String.class);
                question_Text.setText(questionText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                question_Text.setText("Error");
            }
        });

        propA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s1 = dataSnapshot.getValue(String.class);
                tbA.setText(s1);
                tbA.setTextOff(s1);
                tbA.setTextOn(s1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        propB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s2 = dataSnapshot.getValue(String.class);
                tbB.setText(s2);
                tbB.setTextOff(s2);
                tbB.setTextOn(s2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        propC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s3 = dataSnapshot.getValue(String.class);
                tbC.setText(s3);
                tbC.setTextOff(s3);
                tbC.setTextOn(s3);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        propD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s4 = dataSnapshot.getValue(String.class);
                tbD.setText(s4);
                tbD.setTextOff(s4);
                tbD.setTextOn(s4);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chosen = "a";
                increment_id = "countA";
                tbB.setChecked(false);
                tbC.setChecked(false);
                tbD.setChecked(false);
            }
        });


        tbB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosen = "b";
                increment_id = "countB";
                tbA.setChecked(false);
                tbC.setChecked(false);
                tbD.setChecked(false);


            }
        });

        tbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosen = "c";
                increment_id = "countC";
                tbB.setChecked(false);
                tbA.setChecked(false);
                tbD.setChecked(false);


            }
        });


        tbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosen = "d";
                increment_id = "countD";
                tbB.setChecked(false);
                tbC.setChecked(false);
                tbA.setChecked(false);

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update Database
                if (chosen == "a") {
                    data_answer1.runTransaction(new Transaction.Handler() {
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
                            //Log completed transaction
                            Log.d(TAG, "likeTransaction:onComplete" + databaseError);
                        }
                    });
                }
                else if (chosen == "b"){
                    data_answer2.runTransaction(new Transaction.Handler() {
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
                            //Log completed transaction
                            Log.d(TAG, "likeTransaction:onComplete" + databaseError);
                        }
                    });
                }
                else if (chosen == "c"){
                    data_answer3.runTransaction(new Transaction.Handler() {
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
                            //Log completed transaction
                            Log.d(TAG, "likeTransaction:onComplete" + databaseError);
                        }
                    });
                }
                else if (chosen == "d"){
                    data_answer4.runTransaction(new Transaction.Handler() {
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
                            //Log completed transaction
                            Log.d(TAG, "likeTransaction:onComplete" + databaseError);
                        }
                    });

                }

                else {
                    errorText.setVisibility(View.VISIBLE);
                }

                Intent intent1 = new Intent(Student_Quiz.this, Student_Quizlist.class);
                startActivity(intent1);
                finish();
            }
        });

        back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listInent = new Intent(Student_Quiz.this, Student_Quizlist.class);
                startActivity(listInent);
            }
        });
    }
}