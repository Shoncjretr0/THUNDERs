package com.example.thunder;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class postfragment extends Fragment {


    public ImageView camera,gallery,gmap,selector;
    public TextView des,location;
    public EditText dess,locations;
    public Button upload;
    public DatabaseReference databasesell;
    public ArrayList<Uri> ImageList = new ArrayList<Uri>();
    public int uploads = 0;
    static final int PICK_IMG = 1;
    public int CurrentImageSelect = 0;
    public int i=1;
    public String a,b,c,d;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View RootView = inflater.inflate(R.layout.fragment_post, container, false);
        databasesell = FirebaseDatabase.getInstance().getReference("post");



        gallery= RootView.findViewById(R.id.buttongaller);
        camera=RootView.findViewById(R.id.imageViewcamera);
        upload= RootView.findViewById(R.id.buttonuploadd);
        selector=RootView.findViewById(R.id.imageViewselect);
        gmap=RootView.findViewById(R.id.imageViewgmap);
        des=RootView.findViewById(R.id.textViewdes);
                location=RootView.findViewById(R.id.textViewloc);
                dess=RootView.findViewById(R.id.editTextdes);
                        locations=RootView.findViewById(R.id.editTexttag);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();



            }
        });

        return RootView;

    }
    public void choose() {
        //we will pick images
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMG);

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();



                    while (CurrentImageSelect < count) {
                        Uri imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                        ImageList.add(imageuri);
                        CurrentImageSelect = CurrentImageSelect + 1;
                    }

                }

            }

        }

    }
    @SuppressLint("SetTextI18n")
    public void upload() {

        final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("postasset");
        for (uploads=0; uploads < ImageList.size(); uploads++) {
            Uri Image  = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("image/"+Image.getLastPathSegment());

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            SendLink(url);
                        }
                    });

                }
            });


        }

    }
    public void SendLink(String urll){

        if(i==1){
            this.a=urll;
            i=i+1;
            Toast.makeText(getActivity(), "A", Toast.LENGTH_SHORT).show();

        }
        else if(i==2){
            this.b=urll;
            i=i+1;
            Toast.makeText(getActivity(), "B", Toast.LENGTH_SHORT).show();

        }
        else if(i==3){

            this.c=urll;
            i=i+1;
            Toast.makeText(getActivity(), "C", Toast.LENGTH_SHORT).show();

        }
        else{
            this.d=urll;
            i=i+1;
            Toast.makeText(getActivity(), "D", Toast.LENGTH_SHORT).show();

        }






    }






}


