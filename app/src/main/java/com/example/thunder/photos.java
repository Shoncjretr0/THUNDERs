package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class photos extends AppCompatActivity  {

    ListView listviewbuys;
    DatabaseReference databasesell;
    List<post> listbuyy;
    public FirebaseAuth firebaseAuth;
    String nameee=login.name;
    String idd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        Toolbar toolbar = findViewById(R.id.topBr);
        setSupportActionBar(toolbar);

        databasesell = FirebaseDatabase.getInstance().getReference("post");

        listviewbuys = findViewById(R.id.listviewbuy);

        listbuyy = new ArrayList<>();


        Query query = FirebaseDatabase.getInstance().getReference("post");

        query.addListenerForSingleValueEvent(valueEventListener);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:
                        break;

                    case R.id.ic_android:
                        Intent intent0 = new Intent(photos.this, videos.class);
                        startActivity(intent0);

                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(photos.this, search.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(photos.this, postnavigation.class);
                        startActivity(intent3);
                        break;



                    case R.id.ic_profile:
                        Intent intent5 = new Intent(photos.this, profile.class);
                        startActivity(intent5);
                        break;
                }




                return false;
            }
        });
    }

    final ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            listbuyy.clear();
            for (DataSnapshot databasellsn : dataSnapshot.getChildren()) {
                post sellagria = databasellsn.getValue(post.class);

                listbuyy.add(sellagria);
            }
            home_list adapter = new home_list(photos.this, listbuyy);
            listviewbuys.setAdapter( adapter);

            listviewbuys.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                    post listbuy = listbuyy.get(i);

                    return false;
                }
            });

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {


        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent myIntent=new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody="https://shoncj.wordpress.com/2020/03/25/thunder-app/ try this app";
                String shareSub="hope you download it";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,"Share Using"));
                return true;
            case R.id.item2:
                return true;
            case R.id.item3:
                startActivity(new Intent(photos.this, chatmenu.class));
                return true;
            case R.id.item5:
                Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(photos.this, login.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        final DatabaseReference collection = FirebaseDatabase.getInstance().getReference("userprofile");
        Query query = collection.orderByChild("usrproemail").equalTo(nameee);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    final DatabaseReference usrproid = child.child("status").getRef();

                          usrproid.setValue("Onlinee");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }

    @Override
    public void onBackPressed()  {
        super.onPause();
        final DatabaseReference collection = FirebaseDatabase.getInstance().getReference("userprofile");
        Query query = collection.orderByChild("usrproemail").equalTo(nameee);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    final DatabaseReference usrproid = child.child("status").getRef();

                    usrproid.setValue("Offline");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        finish();

    }

}



