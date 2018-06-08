package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Delayed;

import static java.lang.Thread.sleep;

public class QuizzActivity extends AppCompatActivity {

    String sessCode = CreateSessionActivity.sessionCode;
    String rootChild = profEnterSession.sessionCodeTrue;
    public static String quiz_name = QuizList.list_quizName;

    public static String quizChildName;

    boolean childExistsBoolean = true;

    public class Post {
        public int countA;
        public int countB;
        public int countC;
        public int countD;
        public String question;
        public String a;
        public String b;
        public String c;
        public String d;
        public String intialize;

        public Post() {
            //Default Constructor
        }

        public Post(String q, String a, String b, String c, String d, String start) {
            this.countA = 0;
            this.countB = 0;
            this.countC = 0;
            this.countD = 0;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.intialize = start;
            this.question = q;

        }

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("countA", countA);
            result.put("countB", countB);
            result.put("countC", countC);
            result.put("countD", countD);
            result.put("question", question);
            result.put("a", a);
            result.put("b", b);
            result.put("c", c);
            result.put("d", d);
            result.put("initialize", intialize);
            return result;
        }

        public String getQuestion() {
            return question;
        }

        public String getA() {
            return a;
        }

        public String getB() {
            return b;
        }

        public String getC() {
            return c;
        }

        public String getD() {
            return d;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        /*try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //this code is for initializing the text to see the data from firebase
        if (sessCode == null || sessCode.isEmpty()) {
            sessCode = rootChild;
        }

        final DatabaseReference data_name, data_question, data_answer1, data_answer2, data_answer3, data_answer4, startBool;
        final TextView view_name = findViewById(R.id.quizname);
        final TextView view_quiz = findViewById(R.id.Question);
        final TextView view_answer1 = findViewById(R.id.aField);
        final TextView view_answer2 = findViewById(R.id.bField);
        final TextView view_answer3 = findViewById(R.id.cField);
        final TextView view_answer4 = findViewById(R.id.dField);

        final DatabaseReference database = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/").getReference(sessCode);
        data_name = database.child("Quiz").child(quiz_name);
/*
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                childExistsBoolean = dataSnapshot.hasChild("Quiz");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(!childExistsBoolean) {
            return;
        }
*/
        data_question = database.child("Quiz").child(quiz_name).child("question");
        data_answer1 = database.child("Quiz").child(quiz_name).child("a");
        data_answer2 = database.child("Quiz").child(quiz_name).child("b");
        data_answer3 = database.child("Quiz").child(quiz_name).child("c");
        data_answer4 = database.child("Quiz").child(quiz_name).child("d");
        startBool = database.child("Quiz").child(quiz_name).child("start");

        data_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    quizChildName = dataSnapshot.getKey();
                    view_name.setText(quizChildName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       data_question.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String s_quiz = dataSnapshot.getValue().toString();
                    view_quiz.setText(s_quiz);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data_answer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String s_1 = dataSnapshot.getValue(String.class);
                    view_answer1.setText(s_1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        data_answer2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String s2 = dataSnapshot.getValue(String.class);
                    view_answer2.setText(s2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        data_answer3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    view_answer3.setText(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data_answer4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    view_answer4.setText(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Button startButton = findViewById(R.id.Start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(QuizzActivity.this, ResultsActivity.class);
                startActivity(intent2);
                startBool.setValue(true);

            }
        });

        Button backButton = findViewById(R.id.Delete_quiz);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(QuizzActivity.this, QuizList.class);
                data_name.removeValue();
                startActivity(intent3);
            }
        });

    }};

