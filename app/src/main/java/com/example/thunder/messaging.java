package com.example.thunder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class messaging extends AppCompatActivity implements ExampleBottomSheetDialog.BottomSheetListener {

    public TextView namee,userStatus;
    public ImageView pic,attach,send;
    String namme=chatmenu.namee;
     String picurll=chatmenu.picurl;
     String userid=chatmenu.usernamme;
     public static String c,d,e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);


        c=namme;
        d=picurll;
        e=userid;
        Toolbar toolbar = findViewById(R.id.toolbar);
        namee=findViewById(R.id.toolbartext);
        userStatus=findViewById(R.id.status);
        pic=findViewById(R.id.toolbarimg);
        attach=findViewById(R.id.attachment);
        send=findViewById(R.id.send);



        namee.setText(namme);
        Picasso.get().load(picurll).transform(new CropCircleTransformation()).into(pic);

        attach.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");


            }

        });



        pic.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                startActivity(new Intent(messaging.this , userchatprof.class));

            }

        });




    }

    @Override
    public void onButtonClicked(String text) {

    }
}
