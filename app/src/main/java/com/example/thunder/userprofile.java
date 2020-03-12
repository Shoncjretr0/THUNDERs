package com.example.thunder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class userprofile extends AppCompatActivity {
    EditText phoneno;
    EditText nameusr;
    EditText addressusr;
    EditText pincdusr;
    EditText emailusr;
    Button submitt;
    Spinner usertyp;
    DatabaseReference databaseuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        databaseuser= FirebaseDatabase.getInstance().getReference("userprofile");

        phoneno=findViewById(R.id.usrphone);
        nameusr=findViewById(R.id.usrname);
        addressusr=findViewById(R.id.usraddrs);
        pincdusr=findViewById(R.id.pincdusr);
        emailusr=findViewById(R.id.usremail);
        submitt=findViewById(R.id.usrbttn);

        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adduser();
                startActivity(new Intent(userprofile.this , login.class));

            }
        });



    }
    private void adduser(){
        String name=nameusr.getText().toString().trim();
        String phno=phoneno.getText().toString().trim();
        String addr=addressusr.getText().toString().trim();
        String city=pincdusr.getText().toString().trim();
        String email=emailusr.getText().toString().trim();
        String profilepicurl= Objects.requireNonNull(getIntent().getExtras()).getString("value10765");


        if(!TextUtils.isEmpty(name)){

            String id=databaseuser.push().getKey();

            userprofileref usrprofre =new userprofileref(id, name, addr, email, city ,phno,profilepicurl);
            databaseuser.child(id).setValue(usrprofre);
            Toast.makeText(this,"details collected sucessfully",Toast.LENGTH_LONG).show();




        }else{
            Toast.makeText(this,"you should enter name",Toast.LENGTH_LONG).show();
        }
    }
}

