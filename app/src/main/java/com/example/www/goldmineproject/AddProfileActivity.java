package com.example.www.goldmineproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import appdb.Group;
import appdb.User;
import appdb.UserOp;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AddProfileActivity extends AppCompatActivity {
    private CircleImageView imageView;
    private Bitmap file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        setTitle("Новый профиль");
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);
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
        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText name = findViewById(R.id.editUserName);
                final String userName = name.getText().toString();
                if (realm.where(User.class).equalTo("name", userName).findAll().isEmpty()) {
                    final User user = new User(); // Create managed objects directly
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            user.setName(userName);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            file.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            user.setPic(stream.toByteArray());
                            realm.copyToRealm(user);
                        }
                    });
                    Intent intent = new Intent(AddProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(AddProfileActivity.this)
                            .setTitle("Ошибка")
                            .setMessage("Пользователь с таким именем уже существует")
                            .setPositiveButton("Попробовать снова", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    dialog.show();
                }
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
