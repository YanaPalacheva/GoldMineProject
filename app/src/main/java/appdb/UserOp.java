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
    MyCurrency currency;
    double value;
    String commentary;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public MyCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(MyCurrency currency) {
        this.currency = currency;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
