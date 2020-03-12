package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class login extends AppCompatActivity {
    private EditText Name,Password;
    private TextView Info;
    private Button login,Register;
    private TextView forgotpassword;
    private FirebaseAuth firebaseAuth;
    private int counter = 5;
    private ProgressDialog progressDialog;
    static String usermailidpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Name = findViewById(R.id.etname);
        Password = findViewById(R.id.etoassword);
        Info = findViewById(R.id.tvinfo);
        login = findViewById(R.id.btn);
        Register = findViewById(R.id.lginbuttton);
        forgotpassword =  findViewById(R.id.frgpass);




        Info.setText("No of attempts remaining: 5");
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, register.class));


            }

        });


        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);


        FirebaseUser user = firebaseAuth.getCurrentUser();


        if (user != null) {

            finish();

            startActivity(new Intent(login.this, register.class));

        }
        login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                validate(Objects.requireNonNull(Name).getText().toString(), Password.getText().toString());
            }
        });



        forgotpassword.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                startActivity(new Intent(login.this , forgotpassword.class));

            }

        });

    }
    private void validate(String userName, String userPassword) {


        if(userName.isEmpty()||userPassword.isEmpty()) {
            Toast.makeText(login.this, "Enter all deatils", Toast.LENGTH_SHORT).show();

        }
        else {

            progressDialog.setMessage("welcome to AGRIAPP");

            progressDialog.show();


            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override

                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        progressDialog.dismiss();

                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        checkEmailVerification();
                        rrmain();

                    } else {

                        Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show();

                        counter--;

                        Info.setText("No of attempts remaining: " + counter);

                        progressDialog.dismiss();

                        if (counter == 0) {

                            login.setEnabled(false);

                        }

                    }

                }

            });
        }







    }

    private void checkEmailVerification(){

        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();

        Boolean emailflag = firebaseUser.isEmailVerified();



        startActivity(new Intent(login.this, photos.class));
        //rrmain();
        if(emailflag){
            finish();
            startActivity(new Intent(login.this, photos.class));
        }else{

            Toast.makeText(login.this, "Email verified", Toast.LENGTH_SHORT).show();


            firebaseAuth.signOut();
        }





    }

    public void rrmain(){
        usermailidpass=Name.getText().toString();


    }



}




