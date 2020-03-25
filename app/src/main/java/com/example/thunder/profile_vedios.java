package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class profile_vedios extends AppCompatActivity {

    ListView listviewbuys;
    DatabaseReference databasesell;
    List<post> listbuyy;
    String name= login.name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_vedios);
        databasesell = FirebaseDatabase.getInstance().getReference("post_video");

        listviewbuys = findViewById(R.id.listviewbuy);

        listbuyy = new ArrayList<>();


        Query query = FirebaseDatabase.getInstance().getReference("post_video")
                .orderByChild("name")
                .equalTo(name);

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    final ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            listbuyy.clear();
            for (DataSnapshot databasellsn : dataSnapshot.getChildren()) {
                post sellagria = databasellsn.getValue(post.class);

                listbuyy.add(sellagria);
            }
            profilevedio_list adapter = new profilevedio_list(profile_vedios.this, listbuyy);
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
