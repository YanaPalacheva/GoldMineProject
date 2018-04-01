package com.example.www.goldmineproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.www.goldmineproject.adapters.NothingSelectedSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import appdb.CurTotal;
import appdb.CurTotalGroup;
import appdb.Group;
import appdb.GroupOp;
import appdb.MyCurrency;
import appdb.User;
import appdb.UserOp;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Либро on 06.03.2018.
 */

public class AddGroupOpActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_op);
        setTitle("Новая операция");
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);

        Intent intent = getIntent();
        final String groupid = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        final Group group = realm.where(Group.class).equalTo("id", groupid).findFirst();
        List<String> userNames = new ArrayList<>();
        for (User user: group.getUserList())
            userNames.add(user.getName());

        Button addButton = findViewById(R.id.addGroupOpOp);


        final Spinner curr = findViewById(R.id.spinnerCurrencyGroup);
        final Spinner source = findViewById(R.id.groupSpinnerSource);
        final Spinner target = findViewById(R.id.groupSpinnerTarget);
        final EditText total = findViewById(R.id.groupTotal);
        final EditText comment = findViewById(R.id.groupComment);

        ArrayAdapter<String> spinUserAdapterSource = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNames);
        spinUserAdapterSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source.setPrompt("Выберите профиль");
        source.setAdapter(new NothingSelectedSpinnerAdapter(spinUserAdapterSource, R.layout.contact_spinner_row_nothing_selected_source,this));

        ArrayAdapter<String> spinUserAdapterTarget = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNames);
        spinUserAdapterTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target.setPrompt("Выберите профиль");
        target.setAdapter(new NothingSelectedSpinnerAdapter(spinUserAdapterTarget, R.layout.contact_spinner_row_nothing_selected_target,this));


        ArrayAdapter spinCurrAdapter = ArrayAdapter.createFromResource(this, R.array.currs,
                android.R.layout.simple_spinner_item);
        curr.setAdapter(spinCurrAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String comm = comment.getText().toString();
                final double value = Double.parseDouble(total.getText().toString());

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        final GroupOp groupOp = new GroupOp();
                        final MyCurrency currency = realm.where(MyCurrency.class)
                                .equalTo("name", curr.getSelectedItem().toString())
                                .findFirst();
                        final User user1=realm.where(User.class).equalTo("name",source.getSelectedItem().toString())
                                .findFirst();
                        final User user2=realm.where(User.class).equalTo("name",target.getSelectedItem().toString())
                                .findFirst();
                        groupOp.setCurrency(currency);
                        groupOp.setValue(value);
                        groupOp.setGroupid(group.getId());
                        groupOp.setCommentary(comm);
                        groupOp.setSource(user1);
                        groupOp.setTarget(user2);
                        realm.copyToRealm(groupOp);
                        boolean found = false;
                        for (CurTotalGroup curTotalGroup: group.getTotal()) {
                            if ((groupOp.getCurrency().getName().equals(curTotalGroup.getCurrency().getName()))
                                    &&(groupOp.getSource().getName().equals(curTotalGroup.getSource().getName()))
                                    &&(groupOp.getTarget().getName().equals(curTotalGroup.getTarget().getName()))) {
                                curTotalGroup.setValue(curTotalGroup.getValue()+groupOp.getValue());
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            CurTotalGroup curTotalGroup = new CurTotalGroup();
                            curTotalGroup.setGroupid(group.getId());
                            curTotalGroup.setCurrency(groupOp.getCurrency());
                            curTotalGroup.setValue(value);
                            curTotalGroup.setSource(groupOp.getSource());
                            curTotalGroup.setTarget(groupOp.getTarget());
                            realm.copyToRealm(curTotalGroup);
                            group.getTotal().add(curTotalGroup);
                            realm.copyToRealm(group);
                        }
                    }
                });

                Intent intent = new Intent(AddGroupOpActivity.this, GroupOpActivity.class);
                intent.putExtra(EXTRA_MESSAGE, groupid);
                startActivity(intent);
            }
        });
    }
}
