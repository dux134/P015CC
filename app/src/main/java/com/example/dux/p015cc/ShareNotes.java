package com.example.dux.p015cc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShareNotes extends AppCompatActivity {

    private Firebase reference1;
    private ProgressDialog progressDialog;
    private TextView email,mobile,linkAndDesc;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_notes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.share_notesToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://p015cc-917d5.firebaseio.com/SharedNotes/");


        email = findViewById(R.id.shareNotesEmail);
        mobile = findViewById(R.id.shareNotesName);
        linkAndDesc = findViewById(R.id.shareNotesMessage);
        submit = findViewById(R.id.shareNotesButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),"Email cannot be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mobile.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),"Mobile no. cannot be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(linkAndDesc.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),"Link and Description cannot be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog = new ProgressDialog(ShareNotes.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Date currentTime = Calendar.getInstance().getTime();
                Map<String, String> map = new HashMap<String, String>();
                map.put("Email", email.getText().toString());
                map.put("mobile", mobile.getText().toString());
                map.put("linkAndDesc", linkAndDesc.getText().toString());
                map.put("Date", currentTime.toString());
                reference1.push().setValue(map);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ShareNotes.this,Dashboard.class));
                    }
                },1000);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
