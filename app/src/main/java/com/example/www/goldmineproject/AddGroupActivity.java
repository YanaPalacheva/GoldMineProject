package com.example.www.goldmineproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import appdb.Group;
import appdb.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);
        Button addButton = findViewById(R.id.add);



        Spinner userSpinner = findViewById(R.id.personalSpinner);
        final User user = new User(); //TODO: somehow get user from Spinner

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText name = findViewById(R.id.editGroupName);
                final Group group = new Group(); // Create managed objects directly

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        group.setName(name.getText().toString());
                        realm.copyToRealm(group);
                    }
                });
                Intent intent = new Intent(AddGroupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
