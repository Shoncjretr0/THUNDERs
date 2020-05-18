package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
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
    String to=userid;
    String from=login.name;
    DatabaseReference t,gga;
    String iddentifier,ee,ff;
    int aa,bb;
    char cc,dd;
    Query query;
    static int PICK_IMAGE = 123;
    static String lat,lon,piccc,vedio,type,mess,doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);


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

        databaseuser= FirebaseDatabase.getInstance().getReference("message");



        databasesell = FirebaseDatabase.getInstance().getReference("message");

        listviewbuys = findViewById(R.id.listviewbuy);

        listbuyy = new ArrayList<>();

       query = FirebaseDatabase.getInstance().getReference("message")
                .orderByChild("iddentifier")
                .equalTo(iddentifier);


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
                messaage.getText().clear();
                query.addListenerForSingleValueEvent(valueEventListener);

            }
        });

        databaseuser2= FirebaseDatabase.getInstance().getReference("message");
        databaseuser2.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {

                query.addListenerForSingleValueEvent(valueEventListener);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                query.addListenerForSingleValueEvent(valueEventListener);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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


            listviewbuys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {


                    messagepass listbuy = listbuyy.get(i);

                    lat= listbuy.getLat();
                    lon= listbuy.getLon();
                    piccc=listbuy.getPicurl();
                    vedio=listbuy.getVideourl();
                    doc=listbuy.getDocurl();
                    type=listbuy.getType();
                    mess=listbuy.getMessage();


                    if(type.equals("location")){

                        String uri=String.format(Locale.ENGLISH,"geo:%s,%s?q=%s,%s",lat,lon,lat,lon);
                        Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);

                    }
                    else if(type.equals("audio")){


                        startActivity(new Intent(messaging.this, audioshow.class));


                    }
                    else if(type.equals("pdf")){


                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(doc));
                        startActivity(browserIntent);


                    }

                    else if(type.equals("contact")){

                        Uri u = Uri.parse("tel:" + mess);
                        Intent v = new Intent(Intent.ACTION_DIAL, u);

                        try
                        {
                            startActivity(v);
                        }
                        catch (SecurityException s)
                        {
                            Toast.makeText(messaging.this, "error", Toast.LENGTH_SHORT).show();
                        }



                        }


                    else{

                        Toast.makeText(messaging.this, "message", Toast.LENGTH_SHORT).show();
                    }




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
        String lat="unknown";
        String lon="unknown";
        String type="message";
        int seen=0;


        if(!TextUtils.isEmpty(message)){

            String id=databaseuser.push().getKey();

            messagepass usrprofre =new messagepass(id, to, from, message, time , date, picurl, videourl, docurl,iddentifier,lat,lon,type,seen);
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
