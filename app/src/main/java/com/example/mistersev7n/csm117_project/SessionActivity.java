package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
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

public class SessionActivity extends AppCompatActivity {

    private DatabaseReference session;

    private EditText sessionCode;
    private TextView errorMsg;
    private TextView texti;
    private Button submitButton;

    String content;

    private static final String TAG = "SessionActivity";

    public static String sessionCodeTrue;
    private boolean sessionCodeBoolean = false;

    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        session = FirebaseDatabase.getInstance("https://csm117-project.firebaseio.com/").getReference();

        sessionCode = findViewById(R.id.sessCode);

        sessionCode.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        sessionCode.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        texti = findViewById(R.id.hiddenText);
        texti.setVisibility(View.GONE);
        errorMsg = findViewById(R.id.errorText);
        errorMsg.setVisibility(View.GONE);

        submitButton =  findViewById(R.id.buttSubmit);

/*
        sessionCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    if(compareSession()) {
                        Intent intent = new Intent(SessionActivity.this, FeedbackActivity.class);
                        startActivity(intent);
                    }
                    else {
                        findViewById(R.id.errorText).setVisibility(View.VISIBLE);
                    }
                    handled = true;
                }
                return handled;
            }
        });
*/

        session.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                content = sessionCode.getText().toString();
                //boolean hasChild = dataSnapshot.hasChild(content);
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
                    Intent intent = new Intent(SessionActivity.this, FeedbackActivity.class);
                    startActivity(intent);
                }
                else {
                    errorMsg.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
