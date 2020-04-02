package com.example.thunder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class messaging extends AppCompatActivity {

    public TextView namee;
    public ImageView pic;
   public static String namme=chatmenu.namee;
  public static  String picurll=chatmenu.picurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Toolbar toolbar = findViewById(R.id.toolbar);
        namee=findViewById(R.id.toolbartext);
        pic=findViewById(R.id.toolbarimg);

        namee.setText(namme);
        Picasso.get().load(picurll).transform(new CropCircleTransformation()).into(pic);

        pic.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                startActivity(new Intent(messaging.this , userchatprof.class));

            }

        });




    }
}
