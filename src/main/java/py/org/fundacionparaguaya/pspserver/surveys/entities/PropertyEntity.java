package py.org.fundacionparaguaya.pspserver.surveys.entities;

import py.org.fundacionparaguaya.pspserver.surveys.entities.PropertyTitle;
import py.org.fundacionparaguaya.pspserver.surveys.entities.PropertyType;

import javax.persistence.*;

/**
 * Created by rodrigovillalba on 10/18/17.
 */
@Entity
@Table(name = "properties", schema = "data_collect")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated
    private PropertyType type = null;

    @Embedded
    private PropertyTitle title = null;

    private boolean mandatory;


    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public PropertyTitle getTitle() {
        return title;
    }

    public void setTitle(PropertyTitle title) {
        this.title = title;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
