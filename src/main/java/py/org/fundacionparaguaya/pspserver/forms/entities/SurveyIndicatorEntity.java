package py.org.fundacionparaguaya.pspserver.forms.entities;

import javax.persistence.*;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Entity
@Table(schema = "data_collect", name = "survey_indicators")
public class SurveyIndicatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_indicator_id")
    private Long surveyIndicatorId;


    @Column(name = "odk_table_reference")
    private OdkTableReference odkTableReference;

    public Long getSurveyIndicatorId() {
        return surveyIndicatorId;
    }

    public void setSurveyIndicatorId(Long surveyIndicatorId) {
        this.surveyIndicatorId = surveyIndicatorId;
    }

    public OdkTableReference getOdkTableReference() {
        return odkTableReference;
    }

    public void setOdkTableReference(OdkTableReference odkTableReference) {
        this.odkTableReference = odkTableReference;
    }
}
