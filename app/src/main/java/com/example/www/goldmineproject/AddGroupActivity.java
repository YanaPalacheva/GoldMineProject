package com.example.www.goldmineproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.www.goldmineproject.adapters.ProfileAdapter;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import appdb.Group;
import appdb.User;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AddGroupActivity extends AppCompatActivity {
    private CircleImageView imageView;
    private Bitmap file;

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
        //TextView totalCount = findViewById(R.id.groupParticCountTextView);
        imageView = findViewById(R.id.imageView);
        if (savedInstanceState != null) {
            file = savedInstanceState.getParcelable("bitmap");
            imageView.setImageBitmap(file);
        }
        final Context context = this;
        ImagePicker.setMinQuality(600, 600);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage(view);
            }
        });

        final Spinner users = findViewById(R.id.groupParticSpinner);

        RealmResults<User> realmResults = realm.where(User.class).findAll();
        List<User> userList = realm.copyFromRealm(realmResults);
        List<String> userNames = new ArrayList<>();
        for (User user: userList)
            userNames.add(user.getName());
        ArrayAdapter<String> spinUserAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNames);
        users.setAdapter(spinUserAdapter);

        ListView usersSelectedListView = findViewById(R.id.usersSelected);
        final List<User> userArrayList = new ArrayList<>();
        User myProfile=realm.where(User.class)
                    .equalTo("name", "043e0447") //найти себя!!!
                    .findFirst();
        if (myProfile != null)
            userArrayList.add(myProfile);
        final ProfileAdapter userSelectedAdapter = new ProfileAdapter(this, userArrayList, realm);
        usersSelectedListView.setAdapter(userSelectedAdapter);

        //final List<String> usersSelected = new ArrayList<>();
        users.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //usersSelected.add(users.getSelectedItem().toString());
                User user = realm.where(User.class)
                        .equalTo("name", users.getSelectedItem().toString())
                        .findFirst();
                userArrayList.add(user);
                userSelectedAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Выберите хотя бы одного участника", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText name = findViewById(R.id.editGroupName);
                final Group group = new Group(); // Create managed objects directly

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        group.setName(name.getText().toString());
                        if (file != null) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            file.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            group.setPic(stream.toByteArray());
                        }
                        RealmList<User> userRealmList = new RealmList<>();
                        userRealmList.addAll(userArrayList);
                        group.setUserList(userRealmList);
                        realm.copyToRealm(group);
                    }
                });
                Intent intent = new Intent(AddGroupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        file = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        imageView.setImageBitmap(file);
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
}
