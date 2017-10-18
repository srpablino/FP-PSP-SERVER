package py.org.fundacionparaguaya.pspserver.surveys.entities;


import com.google.common.collect.ForwardingMap;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigovillalba on 10/16/17.
 */
@Embeddable
@Access(AccessType.FIELD)
public class SnapshotPropertyEntity implements Serializable {

    private String key;

    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
