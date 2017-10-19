package py.org.fundacionparaguaya.pspserver.surveys.entities;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Parameter;
import py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType;
import py.org.fundacionparaguaya.pspserver.web.models.*;

import javax.persistence.*;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 10/16/17.
 */
@Entity
@Table(name = "surveys", schema = "data_collect")

public class SurveyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "survey_definition")
    @Type(type = "py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType", parameters = {
            @Parameter(name = SecondJSONBUserType.CLASS, value = "py.org.fundacionparaguaya.pspserver.web.models.SurveyDefinition")})
    private SurveyDefinition surveyDefinition;


    public SurveyEntity(){}

    private SurveyEntity(SurveyDefinition definition) {
        this.surveyDefinition = definition;
    }


    public Long getId() {
        return id;
    }

    public SurveyDefinition getSurveyDefinition() {
        return surveyDefinition;
    }


    public void setSurveyDefinition(SurveyDefinition surveyDefinition) {
        this.surveyDefinition = surveyDefinition;
    }

    public static SurveyEntity of(SurveyDefinition definition) {
        checkNotNull(definition);
        return new SurveyEntity(definition);
    }
}
