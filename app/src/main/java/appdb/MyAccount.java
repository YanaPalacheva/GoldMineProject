package appdb;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 46 on 29.12.2017.
 */

public class MyAccount extends RealmObject {
    String name;
    byte[] pic;

    public String getName() {
        return name;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public void setName(String name) {
        this.name = name;
    }
}
