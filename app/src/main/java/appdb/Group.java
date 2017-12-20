package appdb;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www on 20.12.2017.
 */

public class Group extends RealmObject {
    @PrimaryKey
        int id;
    String name;
    byte[] pic;
    RealmList<CurVal> total;
    RealmList<User> userList;
}
