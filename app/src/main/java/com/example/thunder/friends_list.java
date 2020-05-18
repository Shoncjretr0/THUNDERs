package com.example.thunder;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class friends_list extends ArrayAdapter<userprofileref> {

    private Activity context;
    private List<userprofileref> buylistadapters;
    String a, b, c;
    public FirebaseDatabase database;
    public DatabaseReference mReference;
    long count;
    public DatabaseReference childReference;
    ImageView seenimg,onlinesymbol;
    TextView seennum,onlinestate;
    String savedd;
    String state;
    int aa,bb;
    char cc,dd;
    String iddentifier,to,from;
    String saved;
    long messagecount;
    int countt=0;


    public friends_list(Activity context, List<userprofileref> buylistadapters) {
        super(context, R.layout.activity_friends_list, buylistadapters);
        this.context = context;
        this.buylistadapters = buylistadapters;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_friends_list, null, true);

        TextView username = listViewItem.findViewById(R.id.text1);
        ImageView profilepic = listViewItem.findViewById(R.id.imageView);
        seenimg= listViewItem.findViewById(R.id.seenimg);
        seennum=listViewItem.findViewById(R.id.seennumber);
        onlinesymbol=listViewItem.findViewById(R.id.imageView8);
        onlinestate=listViewItem.findViewById(R.id.textView7);




        userprofileref sellG = buylistadapters.get(position);




        username.setText("Username:   " + sellG.getUsrproname());
        b=sellG.getUsrpicurl();
        state=sellG.getStatus();
        if(state.equals("Onlinee")){

            onlinesymbol.setImageResource(R.drawable.ic_brightness_1_black_24dp);
            onlinestate.setText("online");


        }

        Picasso.get().load(b).transform(new CropCircleTransformation()).into(profilepic);

        saved=login.name;
        savedd=sellG.usrproemail;

        if(saved.equals(savedd)){

            Picasso.get().load(R.drawable.view).transform(new CropCircleTransformation()).into(profilepic);
            username.setText("Saved Message");
        }



              //  Query query = FirebaseDatabase.getInstance().getReference("message")
            //    .orderByChild("from")
            //    .equalTo(savedd);
             //    query.orderByChild("seen").equalTo("1");

       // ValueEventListener valueEventListener = new ValueEventListener() {
         //   @Override
         //   public void onDataChange(DataSnapshot dataSnapshot) {
         //       for(DataSnapshot ds : dataSnapshot.getChildren()) {
           //          count = ds.child("seen").getChildrenCount();
             //       Log.d("TAG", "Count: " + count);
               // }
           // }

            //@Override
            //public void onCancelled(@NonNull DatabaseError databaseError) {
              //  Log.d(TAG, databaseError.getMessage());
            //}
        //};
        //query.addListenerForSingleValueEvent(valueEventListener);

      //  if(count>0){
      //      seenimg.setImageResource(R.drawable.round);
      //      seennum.setText((int) count);
   //      }

        to=saved;
        from=savedd;

        aa=from.length();
        bb=to.length();


        if(to.equals(from)) {

            iddentifier=to;
        }
        else if(aa > bb) {

            iddentifier = from+to;
        }
        else if(bb > aa){

            iddentifier= to+from;
        }
        else if(aa==bb){

            cc=from.charAt(3);
            dd=to.charAt(3);
            if(cc>dd){
                iddentifier=from+to;

            }
            else{
                iddentifier=to+from;
            }

        }

        else{
            iddentifier="omb";
        }

        final DatabaseReference collection = FirebaseDatabase.getInstance().getReference("message");
        Query query = collection.orderByChild("iddentifier").equalTo(iddentifier);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                     messagecount = (long)child.child("seen").getValue();

                     if(messagecount==0){

                         countt=countt+1;

                     }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        if(countt>0) {

            seenimg.setImageResource(R.drawable.round);
            seennum.setText(Integer.toString(countt));

        }


        return listViewItem;



    }


}



