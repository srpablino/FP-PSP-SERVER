package py.org.fundacionparaguaya.pspserver.surveys.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType;
import py.org.fundacionparaguaya.pspserver.web.models.SurveyData;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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


    @Column(name = "additional_properties")
    @Type(type = "py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType", parameters = {
            @Parameter(name = SecondJSONBUserType.CLASS, value = "py.org.fundacionparaguaya.pspserver.web.models.SurveyData")})
    private SurveyData additionalProperties;

    @ManyToOne
    private SurveyEntity surveyDefinition;

    @OneToOne
    private FamilyIndicatorEntity familyIndicator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public SurveyData getAdditionalProperties() {
        return additionalProperties;
    }

    public FamilyIndicatorEntity getFamilyIndicator() {
        return familyIndicator;
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

    public SnapshotEconomicEntity surveyIndicator(FamilyIndicatorEntity indicatorEntity) {
        this.familyIndicator = indicatorEntity;
        return this;
    }

    public SurveyEntity getSurveyDefinition() {
        return surveyDefinition;
    }
}
