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

public class QuizList extends AppCompatActivity {

    public static String list_quizName;
    private DatabaseReference data_initialize;
    private ListView listView;
    int iterate = 0;
    //Bundle bundle = getIntent().getExtras();
    //String session_id = bundle.getString("session_code");
    String sessCode = CreateSessionActivity.sessionCode;
    String rootChild = profEnterSession.sessionCodeTrue;

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


    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        if (sessCode == null || sessCode.isEmpty()) {
            sessCode = rootChild;
        }

        data_initialize = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/").getReference(sessCode).child("Quiz");//.child("0").child("initialize");
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
        post = new Post();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list_quizName = (String) parent.getItemAtPosition(position);
                Intent list_intent = new Intent(QuizList.this, QuizzActivity.class);
                //list_intent.putExtra("quiz_name", list_quizName);
                startActivity(list_intent);
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

        Button addquizes = findViewById(R.id.ADD);
        addquizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent(QuizList.this, QuizProfActivity.class);
                startActivity(addintent);
            }
        });

        Button goback = findViewById(R.id.BACK_button);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(QuizList.this, FeedbackResultsActivity.class);
                startActivity(backintent);
            }
        });
    }
}
