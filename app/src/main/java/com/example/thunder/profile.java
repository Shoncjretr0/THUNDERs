package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class profile extends TabActivity {

    ListView listviewbuys;
    DatabaseReference databasesell;
    List<post> listbuyy;
    private FirebaseAuth firebaseAuth;
    public  String  profilepiccc,namee,emailid;
    String name= login.name;
    ImageView pic;
    TextView usrname;
    TextView usrid;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

         pic=findViewById(R.id.imageView3);
        usrname=findViewById(R.id.textView);
         usrid=findViewById(R.id.textView3);
        Toolbar toolbar = findViewById(R.id.topBr);
        context = this;
        br();
        Resources ressources = getResources();
        TabHost tabHost = getTabHost();

        // Android tab
        Intent intentAndroid = new Intent().setClass(this, profile_photos.class);
        TabHost.TabSpec tabSpecAndroid = tabHost
                .newTabSpec("Android")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_photo))
                .setContent(intentAndroid);

        // Apple tab
        Intent intentApple = new Intent().setClass(this, profile_vedios.class);
        TabHost.TabSpec tabSpecApple = tabHost
                .newTabSpec("Apple")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_video))
                .setContent(intentApple);


        databasesell = FirebaseDatabase.getInstance().getReference("userprofile");

        listviewbuys = findViewById(R.id.listviewbuy);

        listbuyy = new ArrayList<>();


        Query query = FirebaseDatabase.getInstance().getReference("userprofile")
                .orderByChild("usrproemail")
                .equalTo(name);

        //query.addListenerForSingleValueEvent(valueEventListener);




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(profile.this, photos.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(profile.this, videos.class);
                        startActivity(intent1);

                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(profile.this, search.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(profile.this, postnavigation.class);
                        startActivity(intent3);
                        break;


                    case R.id.ic_profile:

                        break;
                }


                return false;
            }
        });
        tabHost.addTab(tabSpecAndroid);
        tabHost.addTab(tabSpecApple);


        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(0);
    }
    public void br() {

        final DatabaseReference collection = FirebaseDatabase.getInstance().getReference("userprofile");
        Query query = collection.orderByChild("usrproemail").equalTo(name);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    namee = (String) child.child("usrproname").getValue();
                    profilepiccc= (String) child.child("usrpicurl").getValue();

                    usrname.setText(namee);
                    usrid.setText(name);
                    Picasso.get().load(profilepiccc).transform(new CropCircleTransformation()).into(pic);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }
}


