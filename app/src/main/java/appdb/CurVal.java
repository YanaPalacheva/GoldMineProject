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
