package py.org.fundacionparaguaya.pspserver.surveys.entities;

import com.google.common.collect.Lists;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType;
import py.org.fundacionparaguaya.pspserver.web.models.SurveyData;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Entity
@Table(schema = "data_collect", name = "snapshots_economics")
public class SnapshotEconomicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "economics_mandatory_properties",
            schema = "data_collect"
    )
    private List<SnapshotPropertyEntity> mandataroyProperties = Lists.newLinkedList();

    @ElementCollection
    @CollectionTable(
            name = "economics_optional_properties",
            schema = "data_collect"
    )
    private List<SnapshotPropertyEntity> optionalProperties = Lists.newLinkedList();


    @Column(name = "additional_properties")
    @Type(type = "py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType", parameters = {
            @Parameter(name = SecondJSONBUserType.CLASS, value = "py.org.fundacionparaguaya.pspserver.web.models.SurveyData")})
    private SurveyData additionalProperties;

    @ManyToOne
    private SurveyEntity surveyDefinition;

    @OneToOne
    private SnapshotIndicatorEntity surveyIndicator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Properties getMandataroyProperties() {
        return Properties.of(mandataroyProperties);
    }

    public Properties getOptionalProperties() {
        return Properties.of(optionalProperties);
    }

    public SurveyData getAdditionalProperties() {
        return additionalProperties;
    }

    public SnapshotIndicatorEntity getSurveyIndicator() {
        return surveyIndicator;
    }

    public void setAdditionalProperties(SurveyData additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public SnapshotEconomicEntity surveyDefinition(SurveyEntity definitionEntity) {
        this.surveyDefinition = definitionEntity;
        return this;
    }


    public SnapshotEconomicEntity additionalProperties(SurveyData socioEconomicsSurveyData) {
        this.additionalProperties = socioEconomicsSurveyData;
        return this;
    }

    public SnapshotEconomicEntity surveyIndicator(SnapshotIndicatorEntity indicatorEntity) {
        this.surveyIndicator = indicatorEntity;
        return this;
    }

    public SurveyEntity getSurveyDefinition() {
        return surveyDefinition;
    }
}
