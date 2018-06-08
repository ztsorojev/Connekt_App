package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class QuizProfActivity extends AppCompatActivity {

    private Button mSendData, backButton;
    private FirebaseDatabase database;
    public static String question_name;
    public String quiz_name;


    String sessCode = CreateSessionActivity.sessionCode;
    String rootChild = profEnterSession.sessionCodeTrue;


    public class Post {

        public int countA;
        public int countB;
        public int countC;
        public int countD;
        //public int countE;
        public String question;
        public String a;
        public String b;
        public String c;
        public String d;
        public boolean start;
        //public String e;

        public Post() {
            //Default Constructor
        }

        public Post(String q, String a, String b, String c, String d) {
            this.countA = 0;
            this.countB = 0;
            this.countC = 0;
            this.countD = 0;
           // this.countE = 0;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
           // this.e = e;
            this.question = q;
            this.start = false;

        }

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("countA", countA);
            result.put("countB", countB);
            result.put("countC", countC);
            result.put("countD", countD);
            //result.put("countE", countE);
            result.put("question", question);
            result.put("a", a);
            result.put("b", b);
            result.put("c", c);
            result.put("d", d);
            result.put("start",start);
            //result.put("e", e);
            return result;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_prof);

        if (sessCode == null || sessCode.isEmpty()) {
            sessCode = rootChild;
        }

        final TextView errorText = findViewById(R.id.errorMsg);
        errorText.setVisibility(View.GONE);

        mSendData = (Button) findViewById(R.id.button);
        database = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/");
        final DatabaseReference myRef = database.getReference(sessCode).child("Quiz");

        mSendData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Create a form
                final EditText questionName = (EditText) findViewById(R.id.quizname);
                question_name = questionName.getText().toString();

                final EditText questionField = (EditText) findViewById(R.id.Question);
                String question = questionField.getText().toString();

                final EditText aField = (EditText) findViewById(R.id.aField);
                String a = aField.getText().toString();

                final EditText bField = (EditText) findViewById(R.id.bField);
                String b = bField.getText().toString();
                final EditText cField = (EditText) findViewById(R.id.cField);
                String c = cField.getText().toString();
                final EditText dField = (EditText) findViewById(R.id.dField);
                String d = dField.getText().toString();
                //final EditText eField = (EditText) findViewById(R.id.eField);
               // String e = eField.getText().toString();


                if(question_name.isEmpty() || question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
                    errorText.setVisibility(View.VISIBLE);
                }
                else {
                    Post post = new Post(question, a, b, c, d);
                    Map<String, Object> postValues = post.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(question_name, postValues);
                    //childUpdates.put("/user-posts/" + key, postValues);

                    myRef.updateChildren(childUpdates);

                    myRef.child(question_name).child("countA").setValue( (long) 0);
                    myRef.child(question_name).child("countB").setValue( (long) 0);
                    myRef.child(question_name).child("countC").setValue( (long) 0);
                    myRef.child(question_name).child("countD").setValue( (long) 0);
                    //myRef.child("NEWQUIZ").child("countE").setValue( (long) 0);


                    Intent intent = new Intent(QuizProfActivity.this, QuizList.class);
                    startActivity(intent);
                }


            }



        });

        Button backButton = findViewById(R.id.Back_button_list);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(QuizProfActivity.this, QuizList.class);
                startActivity(intent_back);
            }
        });


    }
}
