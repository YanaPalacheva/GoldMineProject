package appdb;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurTotalGroup extends RealmObject {
    @PrimaryKey
    String id = UUID.randomUUID().toString();
    MyCurrency currency;
    double value;
    String groupid;
    User source;
    User target;


    public String getGroupid() {return groupid;}
    public void setGroupid(String groupid) {this.groupid = groupid;}
    public User getSource() {return source;}
    public void setSource(User source) {this.source = source;}
    public User getTarget() {return target;}
    public void setTarget(User target) {this.target = target;}
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
