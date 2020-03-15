package com.example.thunder;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static android.app.Activity.RESULT_OK;
import static com.example.thunder.postfragment.PICK_IMG;


public class Tab2Fragment extends Fragment {


    private Button btnpost;
    private StorageReference storageReference;
    private DatabaseReference databasesell;
    private FirebaseStorage firebaseStorage;
    public ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private static int PICK_IMAGE = 123;
    public int CurrentImageSelect = 0;
    String a,b,d;
    int uploads=0;
    Uri imageuri;
    public static ImageView imageView,gallery,imgview;
    private EditText des,tag;
    private TextView txtcam,txtgall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AtomicReference<View> view = new AtomicReference<>(inflater.inflate(R.layout.vediofragment, container, false));
        databasesell = FirebaseDatabase.getInstance().getReference("post_video");
        firebaseStorage= FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getInstance().getReference();
        imageView = (ImageView) getActivity().findViewById(R.id.imgcam);
        gallery = (ImageView) getActivity().findViewById(R.id.imggaall);
        imgview = (ImageView) view.get().findViewById(R.id.imagepic);
        des =(EditText) view.get().findViewById(R.id.editTextdes);
        tag=(EditText) view.get().findViewById(R.id.editTexttag);


        btnpost = (Button) view.get().findViewById(R.id.button);
        txtcam = view.get().findViewById(R.id.textViewcam);
        txtgall= view.get().findViewById(R.id.textViewgal);


        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        txtgall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGE);

            }
        });


        return view.get();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                imageuri = data.getData();
                ImageList.add(imageuri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageuri);
                    imgview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }

    }




    public void uploadFile() {
        //checking if file is available
        if (imageuri != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("postasset");
            //adding the file to reference
            Uri Image  = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("videos/"+Image.getLastPathSegment());

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            d=url;
                            Toast.makeText(getActivity().getApplicationContext(), "Post Uploaded ", Toast.LENGTH_LONG).show();
                            //creating the upload object to store uploaded image details
                            post();
                            progressDialog.dismiss();

                        }
                    });



                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(getActivity(), "Upload failed", Toast.LENGTH_SHORT).show();
        }
    }







    public void post() {

        a=des.getText().toString().trim();
        b=tag.getText().toString().trim();



        String name="unknown";

        String photo=d.trim();
        String description=a;
        String tags=b;




        if (!TextUtils.isEmpty(name)) {

            String idsell = databasesell.push().getKey();

            post sellagrii = new post (idsell,name,photo,description,tags);
            databasesell.child(idsell).setValue(sellagrii);
            Toast.makeText(getActivity(), "post sucessful", Toast.LENGTH_SHORT).show();
            getActivity().finish();

        } else {
            Toast.makeText(getActivity(), "You should enter the details", Toast.LENGTH_SHORT).show();
        }

    }
}

