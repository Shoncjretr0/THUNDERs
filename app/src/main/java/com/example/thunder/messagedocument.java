package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.thunder.messaging.PICK_IMAGE;

public class messagedocument extends AppCompatActivity {

    static int PICK_IMAGE = 123;
    Uri imageuri;
    public ArrayList<Uri> ImageList = new ArrayList<Uri>();
    int uploads=0;
    static String d;
    ImageView send,imgview;
    WebView fr;
    String l;
    String datee,f;
    String userid=chatmenu.usernamme;
    String to=userid;
    String from=login.name;
    EditText messaage;
    TextView fgr;
    DatabaseReference databaseuser;
    Date g;
    String iddentifier;
    int aa,bb;
    char cc,dd;
    String o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagedocument);

        send=findViewById(R.id.send);
        messaage=findViewById(R.id.textmessage);
        fr=findViewById(R.id.imagepic);
        g= Calendar.getInstance().getTime();
        f=g.toString();
        fgr=findViewById(R.id.textkl);
        datee=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        databaseuser= FirebaseDatabase.getInstance().getReference("message");

        aa=from.length();
        bb=to.length();


        if(to==from) {

            iddentifier=to;
        }
        else if(aa > bb) {

            iddentifier = from+to;
        }
        else if(bb > aa){

            iddentifier= to+from;
        }
        else if(aa==bb){

            cc=from.charAt(3);
            dd=to.charAt(3);
            if(cc>dd){
                iddentifier=from+to;

            }
            else{
                iddentifier=to+from;
            }

        }

        else{
            iddentifier="omb";
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();

            }
        });
    }

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                imageuri = data.getData();
                ImageList.add(imageuri);

                WebSettings webSettings=fr.getSettings();
                webSettings.setJavaScriptEnabled(true);
                fr.loadUrl(String.valueOf(imageuri));

                Cursor returnCursor = getContentResolver().query(imageuri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                l=returnCursor.getString(nameIndex);
                fgr.setText(returnCursor.getString(nameIndex));



            }

        }





    }



    public void uploadFile() {
        //checking if file is available
        if (imageuri != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(messagedocument.this);
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
                            Toast.makeText(messagedocument.this.getApplicationContext(), "Post Uploaded ", Toast.LENGTH_LONG).show();
                            //creating the upload object to store uploaded image details
                            progressDialog.dismiss();
                            message();

                        }
                    });



                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(messagedocument.this.getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(messagedocument.this, "Upload failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void message(){

        o=messaage.getText().toString().trim() ;

        String message= o + l;
        String time=f;
        String date=datee;
        String picurl="https://firebasestorage.googleapis.com/v0/b/thunder-4bf08.appspot.com/o/postasset%2Fimage%2F1361966090?alt=media&token=171e24e2-c48d-4919-8971-53ada39736a9";
        String videourl="unknown";
        String docurl=d;
        String lat="unknown";
        String lon="unknown";
        String type="pdf";
        int seen=0;


        if(!TextUtils.isEmpty(picurl)){

            String id=databaseuser.push().getKey();

            messagepass usrprofre =new messagepass(id, to, from, message, time , date, picurl, videourl, docurl,iddentifier,lat,lon,type,seen);
            databaseuser.child(id).setValue(usrprofre);
            Toast.makeText(this,"message sent",Toast.LENGTH_LONG).show();
            startActivity(new Intent(messagedocument.this, messaging.class));
            finish();




        }else{
            Toast.makeText(this,"nothing to send",Toast.LENGTH_LONG).show();
            startActivity(new Intent(messagedocument.this, messaging.class));
            finish();
        }
    }

}
