package com.example.thunder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class messagecontact extends AppCompatActivity {

    static final int PICK_CONTACT=1;
    String cNumber;
    TextView namee,contacts;
    String name;
    FloatingActionButton fab;
    String l;
    String datee,f;
    String userid=chatmenu.usernamme;
    String to=userid;
    String from=login.name;
    DatabaseReference databaseuser;
    Date g;
    String iddentifier;
    int aa,bb;
    char cc,dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagecontact);
        namee=findViewById(R.id.contactname);
        contacts=findViewById(R.id.contactnumber);
        fab=findViewById(R.id.floatingActionButton);
        g= Calendar.getInstance().getTime();
        f=g.toString();
        datee=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        databaseuser= FirebaseDatabase.getInstance().getReference("message");
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);



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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message();

            }
        });

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            cNumber = phones.getString(phones.getColumnIndex("data1"));
                            contacts.setText(cNumber);
                        }
                        name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        namee.setText(name);


                    }
                }
                break;
        }
    }
    private void message(){



        String message= cNumber;
        String time=f;
        String date=datee;
        String picurl="https://firebasestorage.googleapis.com/v0/b/thunder-4bf08.appspot.com/o/postasset%2Fimage%2Fimage%3A1769454?alt=media&token=dcdfac3e-6943-4bbb-b34c-7c78ff95ff9a";
        String videourl="unknown";
        String docurl="unknown";
        String lat="unknown";
        String lon="unknown";
        String type="contact";


        if(!TextUtils.isEmpty(picurl)){

            String id=databaseuser.push().getKey();

            messagepass usrprofre =new messagepass(id, to, from, message, time , date, picurl, videourl, docurl,iddentifier,lat,lon,type);
            databaseuser.child(id).setValue(usrprofre);
            Toast.makeText(this,"message sent",Toast.LENGTH_LONG).show();
            startActivity(new Intent(messagecontact.this, messaging.class));
            finish();




        }else{
            Toast.makeText(this,"nothing to send",Toast.LENGTH_LONG).show();
            startActivity(new Intent(messagecontact.this, messaging.class));
            finish();
        }
    }
}
