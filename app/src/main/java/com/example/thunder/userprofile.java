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
    EditText emailusr,des;
    Button submitt;
    Spinner usertyp;
    DatabaseReference databaseuser;
    int value=0;

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
        des=findViewById(R.id.editTextdes);


        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adduser();
                if(value==1) {
                    startActivity(new Intent(userprofile.this, login.class));
                }

            }
        });



    }
    private void adduser(){
        String name=nameusr.getText().toString().trim();
        String phno=phoneno.getText().toString().trim();
        String addr=addressusr.getText().toString().trim();
        String city=pincdusr.getText().toString().trim();
        String email=register.maill;
        String profilepicurl= register.picurll;
        String description=des.getText().toString().trim();
        String status="Online";


        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phno) && !TextUtils.isEmpty(addr) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(description)){

            String id=databaseuser.push().getKey();

            userprofileref usrprofre =new userprofileref(id, name, addr, email, city ,phno,profilepicurl,description,status);
            databaseuser.child(id).setValue(usrprofre);
            Toast.makeText(this,"details collected sucessfully",Toast.LENGTH_LONG).show();
             value=1;


        }else{
            Toast.makeText(this,"you should enter all details",Toast.LENGTH_LONG).show();
            value=0;
        }
    }
}

