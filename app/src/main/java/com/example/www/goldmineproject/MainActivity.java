package com.example.www.goldmineproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.ArrayList;

import appdb.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private float lastX;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        RealmConfiguration config = new RealmConfiguration.Builder().build();

        /*RealmConfiguration config = new RealmConfiguration.Builder()
  .name("myrealm.realm")
  .encryptionKey(getKey())
  .schemaVersion(42)
  .modules(new MySchemaModule())
  .migration(new MyMigration())
  .build();
// Use the config
Realm realm = Realm.getInstance(config);

ЗАПРОСЫ

RealmQuery<User> query = realm.where(User.class);

// Add query conditions:
query.equalTo("name", "John");
query.or().equalTo("name", "Peter");

// Execute the query:
RealmResults<User> result1 = query.findAll();

// Or alternatively do the same all at once (the "Fluent interface"):
RealmResults<User> result2 = realm.where(User.class)
                                  .equalTo("name", "John")
                                  .or()
                                  .equalTo("name", "Peter")
                                  .findAll();*/


        try {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            title = findViewById(R.id.title);

            viewFlipper = findViewById(R.id.flipper);

            FloatingActionButton personalFAB = findViewById(R.id.personalFAB);
            personalFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddPersonalActivity.class);
                    startActivity(intent);
                }
            });
            FloatingActionButton groupFAB = findViewById(R.id.groupFAB);
            groupFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
                    startActivity(intent);
                }
            });
            FloatingActionButton profileFAB = findViewById(R.id.profileFAB);
            profileFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddProfileActivity.class);
                    startActivity(intent);
                }
            });

            ListView profileListView = findViewById(R.id.profileListView);
            //MyAdapter adapter = new MyAdapter(this, realm.where(User.class).findAll());
            //listView.setAdapter(adapter);
        } finally {
            realm.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();
                // Handling left to right screen swap.
                if (lastX < currentX) {
                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
                    // Display next screen.
                    viewFlipper.showNext();
                }
                // Handling right to left screen swap.
                if (lastX > currentX) {
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                if (viewFlipper.getCurrentView() == findViewById(R.id.personalRelLayout)) {
                    title.setText(getResources().getString(R.string.title_activity_personal));
                } else if (viewFlipper.getCurrentView() == findViewById(R.id.groupRelLayout)) {
                    title.setText(getResources().getString(R.string.title_activity_group));
                } else if (viewFlipper.getCurrentView() == findViewById(R.id.profileRelLayout)) {
                    title.setText(getResources().getString(R.string.title_activity_profile));

                }
                    break;
        }
        return false;
    }
}
