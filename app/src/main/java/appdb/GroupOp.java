package appdb;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www on 20.12.2017.
 */

public class GroupOp extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    Group group;
    MyCurrency currency;
    double value;
    String commentary;
    User source;
    User target;
}
