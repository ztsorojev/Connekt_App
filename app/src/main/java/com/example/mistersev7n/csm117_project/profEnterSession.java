package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class profEnterSession extends AppCompatActivity {

    private DatabaseReference session;

    private EditText sessionCode;
    private TextView errorMsg;
    private TextView texti;
    private Button submitButton;

    private static final String TAG = "SessionActivity";

    public static String sessionCodeTrue;
    private boolean sessionCodeBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_enter_session);

        session = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/").getReference();

        sessionCode = findViewById(R.id.sessCode);

        texti = findViewById(R.id.hiddenText);
        texti.setVisibility(View.GONE);
        errorMsg = findViewById(R.id.errorText);
        errorMsg.setVisibility(View.GONE);

        submitButton =  findViewById(R.id.buttSubmit);

        session.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String content = sessionCode.getText().toString();
                sessionCodeBoolean = dataSnapshot.hasChild(content);

                texti.setText(String.valueOf(sessionCodeBoolean));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                texti.setText("Err");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( sessionCodeBoolean ) {
                    sessionCodeTrue = sessionCode.getText().toString();
                    Intent intent = new Intent(profEnterSession.this, FeedbackResultsActivity.class);
                    startActivity(intent);
                }
                else {
                    errorMsg.setVisibility(View.VISIBLE);
                }
            }
        });

    }


}
