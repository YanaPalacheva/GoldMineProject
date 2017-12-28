package com.example.www.goldmineproject;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.www.goldmineproject.adapters.GroupAdapter;
import com.example.www.goldmineproject.adapters.PersonalAdapter;
import com.example.www.goldmineproject.adapters.ProfileAdapter;
import com.mvc.imagepicker.ImagePicker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import appdb.Group;
import appdb.MyCurrency;
import appdb.Tips;
import appdb.User;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private BottomNavigationView bottomNavigationView;
    private float lastX;
    private TextView title;
    private Realm realm;
    private Bitmap file;
    private CircleImageView myProfilePic;

    public Realm getRealm() {
        return realm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .schemaVersion(3)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        //putStuffInRealm(realm);
        //putTipsInRealm(realm);

        /*
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

            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            title = findViewById(R.id.title);

            viewFlipper = findViewById(R.id.flipper);
            bottomNavigationView =findViewById(R.id.navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_acc:
                            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.myProfileRelLayout)));
                            title.setText(getResources().getString(R.string.title_activity_myprofile));
                        break;
                        case R.id.menu_groups:
                            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.profileRelLayout)));
                            title.setText(getResources().getString(R.string.title_activity_profile));
                        break;
                        case R.id.menu_persops:
                            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.personalRelLayout)));
                            title.setText(getResources().getString(R.string.title_activity_personal));
                        break;
                        case R.id.menu_groupops:
                            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.groupRelLayout)));
                            title.setText(getResources().getString(R.string.title_activity_group));
                        break;
                    }
                    return true;
                }
            });

            ListView profileListView = findViewById(R.id.profileListView);
            ProfileAdapter profileAdapter = new ProfileAdapter(this, realm.where(User.class).findAll());
            profileListView.setAdapter(profileAdapter);

            ListView personalListView = findViewById(R.id.personalListView);
            PersonalAdapter personalAdapter = new PersonalAdapter(this, realm.where(User.class).findAll());
            personalListView.setAdapter(personalAdapter);

            ListView groupListView = findViewById(R.id.groupListView);
            GroupAdapter groupAdapter = new GroupAdapter(this, realm.where(Group.class).findAll());
            groupListView.setAdapter(groupAdapter);


            ImageView personalFAB = findViewById(R.id.addPersAccBut);
            personalFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddPersonalActivity.class);
                    startActivity(intent);
                }
            });
            ImageView groupFAB = findViewById(R.id.addGroupOpBut);
            groupFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
                    startActivity(intent);
                }
            });
            ImageView profileFAB = findViewById(R.id.addProfileBut);
            profileFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddProfileActivity.class);
                    startActivity(intent);
                }
            });

        ImagePicker.setMinQuality(600, 600);
        myProfilePic = findViewById(R.id.myProfilePicButton);
        if (savedInstanceState != null) {
            file = savedInstanceState.getParcelable("bitmap");
            myProfilePic.setImageBitmap(file);
        }
        myProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage(view);
            }
        });

        NotificationHelper.scheduleRepeatingRTCNotification(this, 10, 0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }

    /*TODO: settings activity, enable/disable notifications
    public void clickToggleButtonRTC(View view) {
        boolean isEnabled = ((ToggleButton)view).isEnabled();

        if (isEnabled) {
            NotificationHelper.scheduleRepeatingRTCNotification(mContext, hours.getText().toString(), minutes.getText().toString());
            NotificationHelper.enableBootReceiver(mContext);
        } else {
            NotificationHelper.cancelAlarmRTC();
            NotificationHelper.disableBootReceiver(mContext);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        file = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        myProfilePic.setImageBitmap(file);
    }

    public void onPickImage(View view) {
        // Click on image button
        ImagePicker.pickImage(this, "Select your image:");
    }

    @Override
    public void onSaveInstanceState(Bundle toSave) {
        super.onSaveInstanceState(toSave);
        toSave.putParcelable("bitmap", file);
    }

    private void putStuffInRealm(Realm realm){
        final MyCurrency rub = new MyCurrency();
        rub.setName("RUB");
        rub.setRate(1);
        final MyCurrency usd = new MyCurrency();
        usd.setName("USD");
        usd.setRate(56.6);
        final MyCurrency eur = new MyCurrency();
        eur.setName("EUR");
        eur.setRate(65.96);
        final MyCurrency czk = new MyCurrency();
        czk.setName("CZK");
        czk.setRate(2.58);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(rub);
                realm.copyToRealm(usd);
                realm.copyToRealm(eur);
                realm.copyToRealm(czk);
            }
        });
    }

    private void putTipsInRealm(Realm realm){
        final Tips tip1 = new Tips();
        final Tips tip2 = new Tips();
        final Tips tip3 = new Tips();
        final Tips tip4 = new Tips();
        final Tips tip5 = new Tips();
        final Tips tip6 = new Tips();
        final Tips tip7 = new Tips();
        final Tips tip8 = new Tips();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tip1.setTip(getString(R.string.tip_1));
                tip1.setId(1);
                tip2.setTip(getString(R.string.tip_2));
                tip2.setId(2);
                tip3.setTip(getString(R.string.tip_3));
                tip3.setId(3);
                tip4.setTip(getString(R.string.tip_4));
                tip4.setId(4);
                tip5.setTip(getString(R.string.tip_5));
                tip5.setId(5);
                tip6.setTip(getString(R.string.tip_6));
                tip6.setId(6);
                tip7.setTip(getString(R.string.tip_7));
                tip7.setId(7);
                tip8.setTip(getString(R.string.tip_8));
                tip8.setId(8);
                realm.copyToRealm(tip1);
                realm.copyToRealm(tip2);
                realm.copyToRealm(tip3);
                realm.copyToRealm(tip4);
                realm.copyToRealm(tip5);
                realm.copyToRealm(tip6);
                realm.copyToRealm(tip7);
                realm.copyToRealm(tip8);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return onTouchEvent(ev);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_profile clicks here. The action bar will
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
                    bottomNavigationView.setSelectedItemId(R.id.menu_persops);
                } else if (viewFlipper.getCurrentView() == findViewById(R.id.groupRelLayout)) {
                    title.setText(getResources().getString(R.string.title_activity_group));
                    bottomNavigationView.setSelectedItemId(R.id.menu_groupops);
                } else if (viewFlipper.getCurrentView() == findViewById(R.id.profileRelLayout)) {
                    title.setText(getResources().getString(R.string.title_activity_profile));
                    bottomNavigationView.setSelectedItemId(R.id.menu_groups);
                } else if (viewFlipper.getCurrentView() == findViewById(R.id.myProfileRelLayout)) {
                    title.setText(getResources().getString(R.string.title_activity_myprofile));
                    bottomNavigationView.setSelectedItemId(R.id.menu_acc);
                }
                break;
        }
        return false;
    }
}
