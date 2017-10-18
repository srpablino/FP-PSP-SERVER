package py.org.fundacionparaguaya.pspserver.surveys.entities;

import com.google.common.collect.Lists;
import org.hibernate.annotations.*;
import py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType;
import py.org.fundacionparaguaya.pspserver.web.models.SurveyData;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by rodrigovillalba on 10/17/17.
 */
@Entity
@Table(schema = "data_collect", name = "snapshot_indicators")
public class SnapshotIndicatorEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "indicators_mandatory_properties",
            schema = "data_collect"
    )
    private List<SnapshotPropertyEntity> mandataroyProperties = Lists.newLinkedList();

    @ElementCollection
    @CollectionTable(
            name = "indicators_optional_properties",
            schema = "data_collect"
    )
    private List<SnapshotPropertyEntity> recommendedProperties = Lists.newLinkedList();


    @Column(name = "additional_properties")
    @Type(type = "py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType", parameters = {
            @org.hibernate.annotations.Parameter(name = SecondJSONBUserType.CLASS, value = "py.org.fundacionparaguaya.pspserver.web.models.SurveyData")})
    private SurveyData additionalProperties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Properties getMandataroyProperties() {
        return Properties.of(mandataroyProperties);
    }

    public Properties getRecommendedProperties() {
        return Properties.of(recommendedProperties);
    }

    public SurveyData getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(SurveyData additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public SnapshotIndicatorEntity additionalProperties(SurveyData indicatorssSurveyData) {
        this.additionalProperties = indicatorssSurveyData;
        return this;
    }
}
