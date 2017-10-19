package py.org.fundacionparaguaya.pspserver.surveys.entities;


import com.google.common.collect.ForwardingMap;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigovillalba on 10/16/17.
 */
@Entity
@Table(name = "properties_attributes", schema = "data_collect")
public class PropertyAttributeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "property_column_name")
    private String propertyColumnName;

    @Column(name = "stoplight_type")
    @Enumerated(EnumType.STRING)
    private StoptLightType stoptLightType;

    @Embedded
    @Column(name = "property_title")
    private PropertyTitle propertyTitle;

    public PropertyAttributeEntity() {
    }

    public String getPropertyColumnName() {
        return propertyColumnName;
    }

    public void setPropertyColumnName(String propertyColumnName) {
        this.propertyColumnName = propertyColumnName;
    }

    public StoptLightType getStoptLightType() {
        return stoptLightType;
    }

    public void setStoptLightType(StoptLightType stoptLightType) {
        this.stoptLightType = stoptLightType;
    }

    public PropertyTitle getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(PropertyTitle propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
