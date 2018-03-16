package com.example.www.goldmineproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.www.goldmineproject.adapters.FinSumAdapter;
import com.example.www.goldmineproject.adapters.OpAdapter;
import com.mvc.imagepicker.ImagePicker;

import appdb.CurTotal;
import appdb.MyAccount;
import appdb.UserOp;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Либро on 28.02.2018.
 */

public class ProfileActivity extends AppCompatActivity {
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

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        setContentView(R.layout.activity_profile);
        title = findViewById(R.id.title);

        ListView opListView = findViewById(R.id.profLV);
        OpAdapter opAdapter = new OpAdapter(this, realm.where(UserOp.class).findAll());
        opListView.setAdapter(opAdapter);

        ImagePicker.setMinQuality(600, 600);
        myProfilePic = findViewById(R.id.myProfilePicButton);
        if (savedInstanceState != null) {
            file = savedInstanceState.getParcelable("bitmap");
        } else {
            byte[] pic = realm.where(MyAccount.class).findFirst().getPic();
            if (pic != null) {
                file = BitmapFactory.decodeByteArray(pic, 0, pic.length);
            }
        }
        myProfilePic.setImageBitmap(file);
        myProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage(view);

            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
