package com.example.www.goldmineproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import appdb.CurVal;
import appdb.MyCurrency;
import appdb.User;
import appdb.UserOp;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AddPersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);
        Button addButton = findViewById(R.id.add);
        RadioButton myMinusBut = findViewById(R.id.myMinus);
        RadioButton myPlusBut = findViewById(R.id.myPlus);
        myPlusBut.setSelected(true);

        final Spinner users = findViewById(R.id.personalSpinner);
        final Spinner curr = findViewById(R.id.spinnerCurrency);
        final EditText total = findViewById(R.id.persTotal);

        RealmResults<User> realmResults = realm.where(User.class).findAll();
        List<User> userList = realm.copyFromRealm(realmResults);
        List<String> userNames = new ArrayList<>();
        for (User user: userList)
            userNames.add(user.getName());
        ArrayAdapter<String> spinUserAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNames);
        users.setAdapter(spinUserAdapter);

        ArrayAdapter spinCurrAdapter = ArrayAdapter.createFromResource(this, R.array.currs,
                android.R.layout.simple_spinner_item);
        curr.setAdapter(spinCurrAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  User user = realm.where(User.class)
                        .equalTo("name", users.getSelectedItem().toString())
                        .findFirst();
                final UserOp userOp = new UserOp();
                final MyCurrency currency = realm.where(MyCurrency.class)
                        .equalTo("name", curr.getSelectedItem().toString())
                        .findFirst();
                final CurVal curVal = new CurVal();
                curVal.setCurrency(currency);
                curVal.setValue(Double.parseDouble(total.getText().toString()));
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        userOp.setUser(user);
                        userOp.setCurVal(curVal);
                        realm.copyToRealm(userOp);
                    }
                });
                Intent intent = new Intent(AddPersonalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
