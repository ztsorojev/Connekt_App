package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profSessionChoice extends AppCompatActivity {

    Button createSessionButt;
    Button enterSessionButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_session_choice);

        createSessionButt = findViewById(R.id.createSession);
        enterSessionButt = findViewById(R.id.enterSession);


        createSessionButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profSessionChoice.this, CreateSessionActivity.class);
                startActivity(intent);
            }
        });

        enterSessionButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profSessionChoice.this, profEnterSession.class);
                startActivity(intent);
            }
        });
    }
}
