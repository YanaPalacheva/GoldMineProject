package appdb;

import java.util.Currency;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by www on 20.12.2017.
 */

public class CurVal extends RealmObject {
    MyCurrency currency;
    double value;
}
