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

import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

import appdb.Group;
import appdb.User;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Аделя on 28.02.2018.
 */

public class EditProfileActivity extends AppCompatActivity {
    private EditText profileTextView;
    private CircleImageView imageView;
    private Bitmap file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Изменить профиль");
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);
        Button editButton=(Button) findViewById(R.id.editProfile);

        Intent intent=getIntent();
        String profileName=intent.getStringExtra("name");
        profileTextView=(EditText) findViewById(R.id.editUserNameEdit);
        profileTextView.setText(profileName);
        imageView=(CircleImageView) findViewById(R.id.imageViewEditProfile);
        final RealmResults<User> result = realm.where(User.class).equalTo("name", profileName).findAll();
        final User user=result.first();
        if(user.getPic()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPic(), 0, user.getPic().length);
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
                final EditText name = findViewById(R.id.editUserNameEdit);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        user.setName(name.getText().toString());
                        if (file != null) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            file.compress(Bitmap.CompressFormat.PNG, 0, stream);
                            user.setPic(stream.toByteArray());
                        }
                        realm.copyToRealm(user);
                    }
                });
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
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
