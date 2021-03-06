package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    SearchView searchView;
    String output,hhashtag;
    Spinner hashtag;
    ListView listviewbuys;
    DatabaseReference databasesell;
    List<userprofileref> listbuyy;
    List<post> listbuyyy;
    public FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (SearchView) findViewById(R.id.searchView);
        hashtag= findViewById(R.id.filter);
        databasesell = FirebaseDatabase.getInstance().getReference("userprofile");

        listviewbuys = findViewById(R.id.listviewbuy);

        listbuyy = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query!=null){

                    output=query;
                    hhashtag=hashtag.getSelectedItem().toString();
                    if(hhashtag.equals("friends")){


                        databasesell = FirebaseDatabase.getInstance().getReference("userprofile");

                        listviewbuys = findViewById(R.id.listviewbuy);

                        listbuyy = new ArrayList<>();


                        Query querry = FirebaseDatabase.getInstance().getReference("userprofile")
                                .orderByChild("usrproname")
                                .equalTo(output);

                        querry.addListenerForSingleValueEvent(valueEventListener);



                    }
                    else if(hhashtag.equals("Hashtag")){


                        databasesell = FirebaseDatabase.getInstance().getReference("userprofile");

                        listviewbuys = findViewById(R.id.listviewbuy);

                        listbuyy = new ArrayList<>();


                        Query querry1 = FirebaseDatabase.getInstance().getReference("post")
                                .orderByChild("tags")
                                .equalTo(output);

                        querry1.addListenerForSingleValueEvent(valueEventListener1);



                    }


                }else{
                    Toast.makeText(search.this, "text field is empty",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(search.this, photos.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(search.this, videos.class);
                        startActivity(intent1);

                        break;

                    case R.id.ic_books:

                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(search.this, postnavigation.class);
                        startActivity(intent3);
                        break;



                    case R.id.ic_profile:
                        Intent intent5 = new Intent(search.this, profile.class);
                        startActivity(intent5);
                        break;
                }


                return false;
            }
        });

        Query querry = FirebaseDatabase.getInstance().getReference("userprofile");

        querry.addListenerForSingleValueEvent(valueEventListener);

    }
    // Get the intent, verify the action and get the query


         final ValueEventListener valueEventListener = new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 listbuyy.clear();
                 for (DataSnapshot databasellsn : dataSnapshot.getChildren()) {
                     userprofileref sellagria = databasellsn.getValue(userprofileref.class);

                     listbuyy.add(sellagria);
                 }
                 friends_list adapter = new friends_list(search.this, listbuyy);
                 listviewbuys.setAdapter( adapter);

                 listviewbuys.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                     @Override
                     public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                         userprofileref listbuy = listbuyy.get(i);

                         return false;
                     }
                 });

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {


             }

         };

    final ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            listbuyyy.clear();
            for (DataSnapshot databasellsn : dataSnapshot.getChildren()) {
                post sellagria = databasellsn.getValue(post.class);

                listbuyyy.add(sellagria);
            }
            searchhashtag adapter = new searchhashtag(search.this, listbuyyy);
            listviewbuys.setAdapter( adapter);

            listviewbuys.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                    post listbuy = listbuyyy.get(i);

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
                startActivity(new Intent(search.this, chatmenu.class));
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
        startActivity(new Intent(search.this, login.class));
    }

}


