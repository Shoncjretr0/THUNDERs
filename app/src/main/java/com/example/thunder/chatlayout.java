package com.example.thunder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class chatlayout extends ArrayAdapter<messagepass> {

    private Activity context;
    private List<messagepass> buylistadapters;
    String a, b, c,d,e;
    public FirebaseDatabase database;
    public DatabaseReference mReference;
    public DatabaseReference childReference;
    String profilepiccc;

    public chatlayout(Activity context, List<messagepass> buylistadapters) {
        super(context, R.layout.activity_chatlayout, buylistadapters);
        this.context = context;
        this.buylistadapters = buylistadapters;


    }
    @SuppressLint("SetTextI18n")
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_chatlayout, null, true);

        TextView user=listViewItem.findViewById(R.id.messengerTextView);
        TextView message=listViewItem.findViewById(R.id.messageTextView);
        final ImageView userpic=listViewItem.findViewById(R.id.messengerImageView);
        ImageView messagepic=listViewItem.findViewById(R.id.messageImageView);

        messagepass sellG = buylistadapters.get(position);
        a=sellG.getTo();
        b=sellG.getFrom();
        c=sellG.getMessage();
        final DatabaseReference collection = FirebaseDatabase.getInstance().getReference("userprofile");
        Query query = collection.orderByChild("usrproemail").equalTo(b);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    profilepiccc= (String) child.child("usrpicurl").getValue();
                    Picasso.get().load(profilepiccc).transform(new CropCircleTransformation()).into(userpic);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        user.setText(b);
        message.setText(c);
        d = sellG.getPicurl();
        Picasso.get()
                .load(d)
                .into(messagepic);




        return listViewItem;

    }

    }
