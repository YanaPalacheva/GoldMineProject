package appdb;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www on 20.12.2017.
 */

public class GroupOp extends RealmObject {
    @PrimaryKey int id;
    Group group;
    CurVal curVal;
    String commentary;
    User source;
    User target;
}
