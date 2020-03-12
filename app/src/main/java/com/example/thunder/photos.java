package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class photos extends AppCompatActivity {

    ListView listviewbuys;
    DatabaseReference databasesell;
    List<post> listbuyy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

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


}



