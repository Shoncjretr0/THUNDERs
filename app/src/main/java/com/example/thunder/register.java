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


public class register extends AppCompatActivity {
    private EditText mail,password;
    private Button registerr;
    private TextView alrdylgin;
    private ImageView userprofilepic;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    String name,upassword;
    Uri imagePath;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    String d;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userprofilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
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
                                firebaseAuth.signOut();

                                Toast.makeText(register.this, "registration successful", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(register.this, userprofile.class));

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
        if (imagePath != null) {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
            StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
            UploadTask uploadTask = imageReference.putFile(imagePath);
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
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void uploadFile() {
        //checking if file is available
        if (imagePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imagePath));

            //adding the file to reference
            sRef.putFile(imagePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog

                            //uploaded picture url
                            d=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "Profile Image Uploaded ", Toast.LENGTH_LONG).show();
                            rrcd();
                            //creating the upload object to store uploaded image details


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
            Toast.makeText(register.this, "Upload failed", Toast.LENGTH_SHORT).show();
        }
    }
    public void rrcd(){
        String gtf=d;
        Intent i = new Intent(register.this,userprofile.class);
        i.putExtra("value10765",gtf);
        startActivity(i);
        finish();
    }
}



