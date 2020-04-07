package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class chatmenu extends AppCompatActivity {

    SearchView searchView;
    String output,hhashtag;
    Spinner hashtag;
    ListView listviewbuys;
    DatabaseReference databasesell;
    List<userprofileref> listbuyy;
    private FirebaseAuth firebaseAuth;
    public static String namee,picurl,usernamme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (SearchView) findViewById(R.id.searchView);

        databasesell = FirebaseDatabase.getInstance().getReference("userprofile");

        listviewbuys = findViewById(R.id.listviewbuy);

        listbuyy = new ArrayList<>();


        Query querry = FirebaseDatabase.getInstance().getReference("userprofile");

        querry.addListenerForSingleValueEvent(valueEventListener);


    }

    final ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            listbuyy.clear();
            for (DataSnapshot databasellsn : dataSnapshot.getChildren()) {
                userprofileref sellagria = databasellsn.getValue(userprofileref.class);

                listbuyy.add(sellagria);
            }
            friends_list adapter = new friends_list(chatmenu.this, listbuyy);
            listviewbuys.setAdapter( adapter);

            listviewbuys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {



                    userprofileref listbuy=listbuyy.get(i);

                    picurl= listbuy.getUsrpicurl();
                    namee=listbuy.getUsrproname();
                    usernamme=listbuy.getUsrproemail();

                    startActivity(new Intent(chatmenu.this, messaging.class));
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
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}


