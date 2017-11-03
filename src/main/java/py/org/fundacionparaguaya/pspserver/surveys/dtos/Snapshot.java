package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by rodrigovillalba on 10/17/17.
 */
public class Snapshot {

    @JsonProperty("survey_id")
    private Long surveyId = null;

    @JsonProperty("economic_survey_data")
    private SurveyData economicSurveyData = null;

    @JsonProperty("indicators_survey_data")
    private SurveyData indicatorSurveyData = null;

    @JsonProperty("created_at")
    private String createdAt;

    public Snapshot economicSurveyData(SurveyData surveyData) {
        this.economicSurveyData = surveyData;
        return this;
    }

    public Snapshot indicatorSurveyData(SurveyData surveyData) {
        this.indicatorSurveyData = surveyData;
        return this;
    }


    /**
     * Key/value pairs representing the filled out 'Socio Economics' survey
     * @return surveyData
     **/
    @ApiModelProperty(value = "Key/value pairs representing the filled out 'Socio Economics' survey")
    public SurveyData getEconomicSurveyData() {
        return economicSurveyData;
    }

    public void setEconomicSurveyData(SurveyData surveyData) {
        this.economicSurveyData = surveyData;
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
    public SurveyData getIndicatorSurveyData() {
        return indicatorSurveyData;
    }

    public void setIndicatorSurveyData(SurveyData indicatorSurveyData) {
        this.indicatorSurveyData = indicatorSurveyData;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Snapshot {\n");
        sb.append("    surveyId: ").append(toIndentedString(surveyId)).append("\n");
        sb.append("    economicSurveyData: ").append(toIndentedString(economicSurveyData)).append("\n");
        sb.append("    indicatorSurveyData: ").append(toIndentedString(indicatorSurveyData)).append("\n");
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
                com.google.common.base.Objects.equal(this.economicSurveyData, that.economicSurveyData) &&
                com.google.common.base.Objects.equal(this.indicatorSurveyData, that.indicatorSurveyData);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(surveyId, economicSurveyData, indicatorSurveyData);
    }

    public Snapshot snapshotEconomicId(Long id) {
        this.surveyId = id;
        return this;
    }

    public Snapshot surveyId(Long surveyId) {
        this.surveyId = surveyId;
        return this;
    }

    public Snapshot createdAt(String createdAtAsISOString) {
        this.createdAt = createdAtAsISOString;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
