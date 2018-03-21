package appdb;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www on 20.12.2017.
 */

public class GroupOp extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    String groupid;
    MyCurrency currency;
    double value;
    String commentary;
    User source;
    User target;

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public String getGroupid() {return groupid;}
    public void setGroupid(String groupid) {this.groupid = groupid;}
    public MyCurrency getCurrency() { return currency; }
    public String getCommentary() { return commentary; }
    public void setCurrency(MyCurrency currency) { this.currency = currency; }
    public void setCommentary(String commentary) { this.commentary = commentary; }
    public User getSource() {return source; }
    public void setSource(User source) { this.source = source; }
    public User getTarget() { return target; }
    public void setTarget(User target) { this.target = target; }
}
