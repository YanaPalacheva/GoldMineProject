package com.example.www.goldmineproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import appdb.Tips;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TipDialogActivity extends Activity {
    @Override
    protected void onStart() {
        super.onStart();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH) % 8 + 1;
        String text = realm.where(Tips.class).equalTo("id", day).findAll().get(0).getTip();

        TextView textView = findViewById(R.id.dialogText);
        textView.setText(text);
        Button button = findViewById(R.id.okButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipDialogActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}