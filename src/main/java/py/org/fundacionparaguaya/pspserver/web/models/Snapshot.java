package py.org.fundacionparaguaya.pspserver.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by rodrigovillalba on 10/17/17.
 */
public class Snapshot {

    @JsonProperty("survey_id")
    private Long surveyId = null;

    @JsonProperty("socio_econocmics_survey_data")
    private SurveyData socioEconomicsSurveyData = null;

    @JsonProperty("indicators_survey_data")
    private SurveyData indicatorssSurveyData = null;

    public Snapshot socioEconomicsSurveyData(SurveyData surveyData) {
        this.socioEconomicsSurveyData = surveyData;
        return this;
    }

    public Snapshot indicatorsSurveyData(SurveyData surveyData) {
        this.indicatorssSurveyData = surveyData;
        return this;
    }


    /**
     * Key/value pairs representing the filled out 'Socio Economics' survey
     * @return surveyData
     **/
    @ApiModelProperty(value = "Key/value pairs representing the filled out 'Socio Economics' survey")
    public SurveyData getSocioEconomicsSurveyData() {
        return socioEconomicsSurveyData;
    }

    public void setSocioEconomicsSurveyData(SurveyData surveyData) {
        this.socioEconomicsSurveyData = surveyData;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * Key/value pairs representing the filled out 'Indicators' survey
     * @return surveyData
     **/
    @ApiModelProperty(value = "Key/value pairs representing the filled out 'Indicators' survey")
    public SurveyData getIndicatorssSurveyData() {
        return indicatorssSurveyData;
    }

    public void setIndicatorssSurveyData(SurveyData indicatorssSurveyData) {
        this.indicatorssSurveyData = indicatorssSurveyData;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Snapshot {\n");
        sb.append("    surveyId: ").append(toIndentedString(surveyId)).append("\n");
        sb.append("    socioEconomicsSurveyData: ").append(toIndentedString(socioEconomicsSurveyData)).append("\n");
        sb.append("    indicatorssSurveyData: ").append(toIndentedString(indicatorssSurveyData)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snapshot that = (Snapshot) o;

        return com.google.common.base.Objects.equal(this.surveyId, that.surveyId) &&
                com.google.common.base.Objects.equal(this.socioEconomicsSurveyData, that.socioEconomicsSurveyData) &&
                com.google.common.base.Objects.equal(this.indicatorssSurveyData, that.indicatorssSurveyData);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(surveyId, socioEconomicsSurveyData, indicatorssSurveyData);
    }

    public Snapshot snapshotEconomicId(Long id) {
        this.surveyId = id;
        return this;
    }

    public Snapshot surveyId(Long surveyId) {
        this.surveyId = surveyId;
        return this;
    }
}
