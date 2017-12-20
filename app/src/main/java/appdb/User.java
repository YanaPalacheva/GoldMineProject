package appdb;

import android.graphics.Picture;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www on 20.12.2017.
 */

/*Bitmap bmp = intent.getExtras().get("data");
ByteArrayOutputStream stream = new ByteArrayOutputStream();
bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
byte[] byteArray = stream.toByteArray();*/

public class User extends RealmObject {
    @PrimaryKey long id;
    String name;
    byte[] pic;
    RealmList<CurVal> total;

    public void setName(String name) {
        this.name = name;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public byte[] getPic() {
        return pic;
    }

    public long getId() {
        return id;
    }

    public RealmList<CurVal> getTotal() {
        return total;
    }

    public String getName() {
        return name;
    }
}

