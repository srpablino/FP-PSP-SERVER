package py.org.fundacionparaguaya.pspserver.surveys.entities;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Parameter;
import py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;

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
            @Parameter(name = SecondJSONBUserType.CLASS, value = "py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyDefinition")})
    private SurveyDefinition surveyDefinition;


    public SurveyEntity(){}

    private SurveyEntity(String title, String description, SurveyDefinition definition){
        this.title = title;
        this.description = description;
        this.surveyDefinition = definition;
    }


    public SurveyEntity(Long surveyId) {
        this.id = surveyId;
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

    public static SurveyEntity of(String title, String description, SurveyDefinition definition) {
        checkNotNull(definition);
        return new SurveyEntity(title, description, definition);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
