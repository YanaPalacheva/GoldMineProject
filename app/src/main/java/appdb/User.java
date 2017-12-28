package appdb;

import android.graphics.Picture;

import java.util.UUID;

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
    @PrimaryKey String id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
    }

    public double getTotal() {
        double apprSum=0;
        for (CurVal cv: total)
            apprSum+=cv.getValue()*cv.getCurrency().getRate();
        return apprSum;
    }

    public String getName() {
        return name;
    }
}

