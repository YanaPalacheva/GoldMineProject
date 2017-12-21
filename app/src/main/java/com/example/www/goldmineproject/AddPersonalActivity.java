package com.example.www.goldmineproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import appdb.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddPersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal);RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);
        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Spinner name = findViewById(R.id.spinner);
                final User user = new User(); //TODO: somehow get user from Spinner
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        user.setName("user"); //remove later
                        realm.copyToRealm(user);
                    }
                });
                Intent intent = new Intent(AddPersonalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
