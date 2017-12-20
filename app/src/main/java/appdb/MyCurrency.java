package appdb;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www on 20.12.2017.
 */

public class MyCurrency extends RealmObject {
    @PrimaryKey int id;
    String name;
    double rate;
}
