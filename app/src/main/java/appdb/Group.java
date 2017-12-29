package appdb;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www on 20.12.2017.
 */

public class Group extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    String name;
    byte[] pic;
    RealmList<CurTotal> total;
    RealmList<User> userList;

    public byte[] getPic() {
        return pic;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RealmList<CurTotal> getTotal() {
        return total;
    }

    public RealmList<User> getUserList() {
        return userList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public void setTotal(RealmList<CurTotal> total) {
        this.total = total;
    }

    public void setUserList(RealmList<User> userList) {
        this.userList = userList;
    }
}
