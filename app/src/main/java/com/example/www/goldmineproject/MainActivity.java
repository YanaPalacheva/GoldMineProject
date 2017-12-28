package com.example.www.goldmineproject;

import android.content.Intent;
import android.os.Bundle;
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

import appdb.Group;
import appdb.MyCurrency;
import appdb.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private BottomNavigationView bottomNavigationView;
    private float lastX;
    private TextView title;
    private Realm realm;

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

            putStuffInRealm(realm);

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
