package com.example.www.goldmineproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import appdb.CurTotal;
import appdb.MyCurrency;
import appdb.User;
import appdb.UserOp;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import android.widget.Toast;

public class AddPersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);
        Button addButton = findViewById(R.id.add);
       final RadioButton myMinusBut = findViewById(R.id.myMinus);
        final RadioButton myPlusBut = findViewById(R.id.myPlus);
       final RadioGroup radioGroupDebt = findViewById(R.id.radioGroup2);

        final Spinner users = findViewById(R.id.personalSpinner);
        final Spinner curr = findViewById(R.id.spinnerCurrency);
        final EditText total = findViewById(R.id.persTotal);
        final EditText comment = findViewById(R.id.persComment);

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
                if (users.getSelectedItem() == null || total.getText().toString().equals("")) {//ошибка во втором условии
                    /*Toast toast = Toast.makeText(AddPersonalActivity.this,"Выберите с кем и сумму", Toast.LENGTH_SHORT);
                    toast.show();*/
                    AlertDialog dialog = new AlertDialog.Builder(AddPersonalActivity.this)
                            .setTitle("Ошибка")
                            .setMessage("Выберите с кем и сумму")
                            .setPositiveButton("Попробовать снова", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    dialog.show();
                }
                else
                {
                    Double value;
                    final String comm = comment.getText().toString();
                    if (radioGroupDebt.getCheckedRadioButtonId() == (myPlusBut.getId())) {
                        value = Double.valueOf(total.getText().toString());
                    } else {
                        value = -Double.valueOf(total.getText().toString());
                    }
                    final Double val = value;
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            final User user = realm.where(User.class)
                                    .equalTo("name", users.getSelectedItem().toString())
                                    .findFirst();
                            final UserOp userOp = new UserOp();
                            final MyCurrency currency = realm.where(MyCurrency.class)
                                    .equalTo("name", curr.getSelectedItem().toString())
                                    .findFirst();
                            userOp.setCurrency(currency);
                            userOp.setValue(val);
                            userOp.setUserid(user.getId());
                            userOp.setCommentary(comm);
                            realm.copyToRealm(userOp);
                            boolean found = false;
                            for (CurTotal curTotal : user.getTotalList()) {
                                if (userOp.getCurrency().getName().equals(curTotal.getCurrency().getName())) {
                                    curTotal.setValue(curTotal.getValue() + userOp.getValue());
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                CurTotal curTotal = new CurTotal();
                                curTotal.setUserid(user.getId());
                                curTotal.setCurrency(userOp.getCurrency());
                                curTotal.setValue(userOp.getValue());
                                user.getTotalList().add(curTotal);
                            }
                        }
                    });
                    Intent intent = new Intent(AddPersonalActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
