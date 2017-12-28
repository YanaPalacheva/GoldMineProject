package appdb;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www on 20.12.2017.
 */

public class UserOp extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    User user;
    CurVal curVal;
    String commentary;

    public void setUser(User user) {
        this.user = user;
    }

    public void setCurVal(CurVal curVal) {
        this.curVal = curVal;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
