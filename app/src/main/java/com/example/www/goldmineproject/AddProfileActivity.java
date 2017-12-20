package com.example.www.goldmineproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import appdb.User;
import io.realm.Realm;

public class AddProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Realm realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_add_profile);
        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText name = findViewById(R.id.editUserName);
                User user = realm.createObject(User.class); // Create managed objects directly
                user.setName(name.getText().toString());
                //TODO: user.setPic();
                realm.commitTransaction();
                Intent intent = new Intent(AddProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
