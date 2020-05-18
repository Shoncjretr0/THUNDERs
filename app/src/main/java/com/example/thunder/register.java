package com.example.thunder;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;


public class register extends AppCompatActivity {
    private EditText mail,password;
    private Button registerr;
    private TextView alrdylgin;
    private ImageView userprofilepic;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    String urll="";
    int uploads=0;
    private static int PICK_IMAGE = 123;
    public ArrayList<Uri> ImageList = new ArrayList<Uri>();
    String name,upassword;
    Uri imageuri;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    String d;
    public static String picurll;
    public static String maill;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                imageuri = data.getData();
                ImageList.add(imageuri);
                urll="set";

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                    userprofilepic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage= FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getInstance().getReference();

        userprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });




        registerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String user_mail = mail.getText().toString().trim();
                    String user_password = password.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(user_mail, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                //sendEmailVerification();
                                //sendUserData();
                                uploadFile();
                                maill=mail.getText().toString();



                            } else {
                                Toast.makeText(register.this, "registration failed", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
                }
            }

        });


        alrdylgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, MainActivity.class));


            }
        });
    }
    private void setupUIViews(){
        mail=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        userprofilepic=findViewById(R.id.usrprofpicagri);
        registerr=findViewById(R.id.rgstr);
        alrdylgin=findViewById(R.id.tvuserlogin);

    }
    private Boolean validate(){
        boolean result=false;
        name=mail.getText().toString();
        upassword =password.getText().toString();




        if(name.isEmpty() || upassword.isEmpty() ) {
            Toast.makeText(register.this, "please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if(upassword.length()<6){
            Toast.makeText(register.this, "password length should be greater than 6  ", Toast.LENGTH_SHORT).show();

        }
        else if(urll.isEmpty()){
            Toast.makeText(register.this, "select a profile picture ", Toast.LENGTH_SHORT).show();

        }
        else {
            result = true;
        }
        return result;


    }
    private void sendEmailVerification(){

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(register.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(register.this, MainActivity.class));
                    }else{
                        Toast.makeText(register.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void sendUserData() {
        if (imageuri != null) {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
            StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
            UploadTask uploadTask = imageReference.putFile(imageuri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(register.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(register.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public void uploadFile() {
        //checking if file is available
        if (imageuri != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(register.this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("postasset");
            //adding the file to reference
            Uri Image  = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("image/"+Image.getLastPathSegment());

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            d=url;
                            Toast.makeText(getApplicationContext(), "Profile pic uploaded", Toast.LENGTH_LONG).show();


                            Toast.makeText(register.this, "registration successful", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            progressDialog.dismiss();
                            //creating the upload object to store uploaded image details
                            rrcd();

                            startActivity(new Intent(register.this, userprofile.class));
                            finish();

                        }
                    });



                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            Toast.makeText( register.this,"Upload failed", Toast.LENGTH_SHORT).show();
        }
    }
    public void rrcd(){

        picurll=d;

    }
}



