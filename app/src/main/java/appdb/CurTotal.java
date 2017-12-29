package appdb;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurTotal extends RealmObject {
    @PrimaryKey
    String id = UUID.randomUUID().toString();
    MyCurrency currency;
    double value;
    User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public void setCurrency(MyCurrency currency) {
        this.currency = currency;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public MyCurrency getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }
}
