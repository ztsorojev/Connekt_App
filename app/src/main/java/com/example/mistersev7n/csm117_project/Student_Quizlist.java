package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student_Quizlist extends AppCompatActivity {

    public static String list_quizName;
    private DatabaseReference data_initialize;
    private ListView listView;


    String rootChild = SessionActivity.sessionCodeTrue;
    public static String student_quizId;

    //Array List
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

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
        public String initialize;

        public Post() {
            //Default Constructor
        }

        public Post(String a, String b, String c, int countA, int countB, int countC, int countD, String d, String init, String q) {
            this.countA = countA;
            this.countB = countB;
            this.countC = countC;
            this.countD = countD;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.initialize = init;
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
            result.put("initialize", initialize);
            return result;
        }

        public String getInitialize() {
            return initialize;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__quizlist);
        if (rootChild == null || rootChild.isEmpty()) {
            return;
        }

        Button back_button = findViewById(R.id.button_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        data_initialize = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/").getReference(rootChild).child("Quiz");//.child("0").child("initialize");
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list_quizName = (String) parent.getItemAtPosition(position);
                final DatabaseReference start_data = data_initialize.child(list_quizName);
                start_data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean check = dataSnapshot.child("start").getValue(Boolean.class);
                        if(check) {
                            student_quizId = list_quizName;
                            Intent list_intent = new Intent(Student_Quizlist.this, Student_Quiz.class);
                            startActivity(list_intent);
                        }
                        else {
                            Toast toast = Toast.makeText(Student_Quizlist.this, "Quiz is not available", Toast.LENGTH_SHORT);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                            v.setTextColor(Color.WHITE);
                            toast.show();
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });




        data_initialize.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ss) {
                for(DataSnapshot ds : ss.getChildren()) {
                    String check1 = ds.getKey();
                    //String check1 = ds.getValue(String.class);
                    arrayList.add(check1);
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
