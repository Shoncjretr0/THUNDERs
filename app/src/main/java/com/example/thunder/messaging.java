package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class messaging extends AppCompatActivity implements ExampleBottomSheetDialog.BottomSheetListener {

    public TextView namee,userStatus;
    public EditText messaage;
    public ImageView pic,attach,send;
    String namme=chatmenu.namee;
     String picurll=chatmenu.picurl;
     String userid=chatmenu.usernamme;
     public static String c,d,e,f,datee;
     Date g;
    DatabaseReference databaseuser,databaseuser2;
    ListView listviewbuys;
    DatabaseReference databasesell;
    List<messagepass> listbuyy;
    String to=namme;
    String from=login.name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        databaseuser= FirebaseDatabase.getInstance().getReference("message").child(from).child(to);
        databaseuser2= FirebaseDatabase.getInstance().getReference("message").child(to).child(from);


        databasesell = FirebaseDatabase.getInstance().getReference("message");

        listviewbuys = findViewById(R.id.listviewbuy);

        listbuyy = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference("message").child(from).child(to);


        query.addListenerForSingleValueEvent(valueEventListener);


        c=namme;
        d=picurll;
        e=userid;
        Toolbar toolbar = findViewById(R.id.toolbar);
        namee=findViewById(R.id.toolbartext);
        userStatus=findViewById(R.id.status);
        pic=findViewById(R.id.toolbarimg);
        attach=findViewById(R.id.attachment);
        send=findViewById(R.id.send);
        messaage=findViewById(R.id.textmessage);

        g= Calendar.getInstance().getTime();
        f=g.toString();
        datee=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());



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


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message();
                message1();
                messaage.getText().clear();
            }
        });




    }
    final ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            listbuyy.clear();
            for (DataSnapshot databasellsn : dataSnapshot.getChildren()) {
                messagepass sellagria = databasellsn.getValue(messagepass.class);

                listbuyy.add(sellagria);
            }
            chatlayout adapter = new chatlayout(messaging.this, listbuyy);
            listviewbuys.setAdapter( adapter);

            listviewbuys.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                    messagepass listbuy = listbuyy.get(i);

                    return false;
                }
            });

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {


        }

    };


    private void message(){

        String message=messaage.getText().toString().trim();
        String time=f;
        String date=datee;
        String picurl="unknown";
        String videourl="unknown";
        String docurl="unknown";


        if(!TextUtils.isEmpty(message)){

            String id=databaseuser.push().getKey();

            userprofileref usrprofre =new userprofileref(id, to, from, message, time , date, picurl, videourl, docurl);
            databaseuser.child(id).setValue(usrprofre);
            Toast.makeText(this,"message sent",Toast.LENGTH_LONG).show();




        }else{
            Toast.makeText(this,"nothing to send",Toast.LENGTH_LONG).show();
        }
    }

    private void message1(){

        String message=messaage.getText().toString().trim();
        String time=f;
        String date=datee;
        String picurl="unknown";
        String videourl="unknown";
        String docurl="unknown";


        if(!TextUtils.isEmpty(message)){

            String id=databaseuser.push().getKey();

            userprofileref usrprofre =new userprofileref(id, to, from, message, time , date, picurl, videourl, docurl);
            databaseuser.child(id).setValue(usrprofre);
            Toast.makeText(this,"message sent",Toast.LENGTH_LONG).show();




        }else{
            Toast.makeText(this,"nothing to send",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onButtonClicked(String text) {

    }
}
