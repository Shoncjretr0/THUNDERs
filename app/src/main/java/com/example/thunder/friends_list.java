package com.example.thunder;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class friends_list extends ArrayAdapter<userprofileref> {

    private Activity context;
    private List<userprofileref> buylistadapters;
    String a, b, c;
    public FirebaseDatabase database;
    public DatabaseReference mReference;
    public DatabaseReference childReference;


    public friends_list(Activity context, List<userprofileref> buylistadapters) {
        super(context, R.layout.home_list, buylistadapters);
        this.context = context;
        this.buylistadapters = buylistadapters;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.home_list, null, true);

        TextView username = listViewItem.findViewById(R.id.text1);
        ImageView profilepic = listViewItem.findViewById(R.id.imageView);


        userprofileref sellG = buylistadapters.get(position);




        username.setText("Username:   " + sellG.getUsrproname());
        b=sellG.getUsrpicurl();


        Picasso.get().load(b).transform(new CropCircleTransformation()).into(profilepic);


        return listViewItem;


    }


}



