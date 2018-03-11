package com.example.www.goldmineproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

import appdb.Group;
import appdb.User;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Аделя on 28.02.2018.
 */

public class EditGroupActivity extends AppCompatActivity {

    private EditText groupTextView;
    private CircleImageView imageView;
    private Bitmap file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        setTitle("Изменить групповой счёт");
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);
        Button editButton=(Button) findViewById(R.id.editButtonGroup);

        Intent intent=getIntent();
        String groupName=intent.getStringExtra("name");
        groupTextView=(EditText) findViewById(R.id.editGroupNameEdit);
        groupTextView.setText(groupName);
        imageView=(CircleImageView) findViewById(R.id.imageViewEdit);
        final RealmResults<Group> result = realm.where(Group.class).equalTo("name", groupName).findAll();
        final Group group=result.first();
        if(group.getPic()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(group.getPic(), 0, group.getPic().length);
            imageView.setImageBitmap(bitmap);
        }
        if (savedInstanceState != null) {
            file = savedInstanceState.getParcelable("bitmap");
            imageView.setImageBitmap(file);
        }
        ImagePicker.setMinQuality(600, 600);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage(view);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText name = findViewById(R.id.editGroupNameEdit);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        group.setName(name.getText().toString());
                        if (file != null) {
                            file=Bitmap.createScaledBitmap(file, file.getWidth()/4, file.getHeight()/4, false);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            file.compress(Bitmap.CompressFormat.PNG, 0, stream);
                            group.setPic(stream.toByteArray());
                        }
                        realm.copyToRealm(group);
                    }
                });
                Intent intent = new Intent(EditGroupActivity.this, MainActivity.class);
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
