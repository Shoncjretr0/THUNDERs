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

public class profilephotolist extends ArrayAdapter<post> {

    private Activity context;
    private List<post> buylistadapters;
    String a, b, c;
    public FirebaseDatabase database;
    public DatabaseReference mReference;
    public DatabaseReference childReference;


    public profilephotolist(Activity context, List<post> buylistadapters) {
        super(context, R.layout.activity_profilephotolist, buylistadapters);
        this.context = context;
        this.buylistadapters = buylistadapters;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.home_list, null, true);


        ImageView pic = listViewItem.findViewById(R.id.imageView1);


        post sellG = buylistadapters.get(position);


        a = sellG.getPhotos();
        Picasso.get()
                .load(a)
                .into(pic);




        return listViewItem;


    }


}



