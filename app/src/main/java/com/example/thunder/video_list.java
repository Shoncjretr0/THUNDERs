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
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class video_list extends ArrayAdapter<post> {

    private Activity context;
    private List<post> buylistadapters;
    String a,b;
    public FirebaseDatabase database ;
    public DatabaseReference mReference ;
    public DatabaseReference childReference ;

    public video_list (Activity context,List<post>  buylistadapters){
        super(context,R.layout.activity_video_list, buylistadapters);
        this.context=context;
        this.buylistadapters=buylistadapters;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_video_list,null,true);

        TextView username= listViewItem.findViewById(R.id.text1);
        TextView name= listViewItem.findViewById(R.id.text2);
        ImageView profilepic= listViewItem.findViewById(R.id.imageView);
        VideoView pic= listViewItem.findViewById(R.id.imageView1);


        post sellG= buylistadapters.get(position);

        username.setText( "Username:   " + sellG.getName());
        name.setText( "Name:   "+ sellG.getDescription());
        a=sellG.getPhotos();
        pic.setVideoPath(a);
        pic.start();







        return listViewItem;







    }


}
