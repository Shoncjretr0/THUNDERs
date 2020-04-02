package com.example.thunder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class userchatprof extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    public ImageView i;
    String picurl=messaging.picurll;
    String nameee=messaging.namme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userchatprof);
        i=findViewById(R.id.img);
        Picasso.get().load(picurl).transform(new CropCircleTransformation()).into(i);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(nameee);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinateLayout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if(state.equals(State.COLLAPSED)) {
                    toolbar.setBackgroundResource(R.drawable.a);
                }
                else if (state.equals(State.EXPANDED)) {
                    toolbar.setBackgroundResource(R.color.transparent);

                }
                else if ((state.equals(State.IDLE))){
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Snackbar.make(coordinatorLayout, "Back button pressed", Snackbar.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}