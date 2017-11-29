package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class SnapshotIndicators {
    
    @JsonProperty("family_data")
    private SurveyData familyData = null;

    @JsonProperty("indicators_survey_data")
    private List<SurveyData> indicatorsSurveyData = null;

    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("count_red_indicators")
    private Integer countRedIndicators = 0;
    
    @JsonProperty("count_yellow_indicators")
    private Integer countYellowIndicators = 0;
    
    @JsonProperty("count_green_indicators")
    private Integer countGreenIndicators = 0;
    
 
    public SnapshotIndicators familyData(SurveyData surveyData) {
        this.familyData = surveyData;
        return this;
    }

    public SnapshotIndicators indicatorSurveyData(List<SurveyData> indicatorsSurveyData) {
        this.indicatorsSurveyData = indicatorsSurveyData;
        return this;
    }
    
    public SnapshotIndicators createdAt(String createdAtAsISOString) {
        this.createdAt = createdAtAsISOString;
        return this;
    }
    
    public SnapshotIndicators countRedIndicators(Integer countRedIndicators) {
        this.countRedIndicators = countRedIndicators;
        return this;
    }
    
    public SnapshotIndicators countYellowIndicators(Integer countYellowIndicators) {
        this.countYellowIndicators = countYellowIndicators;
        return this;
    }
    
    public SnapshotIndicators countGreenIndicators(Integer countGreenIndicators) {
        this.countGreenIndicators = countGreenIndicators;
        return this;
    }
    
    @ApiModelProperty(value = "Key/value pairs representing the family data")
    public SurveyData getfamilyData() {
        return familyData;
    }

    public void setFamilyData(SurveyData surveyData) {
        this.familyData = surveyData;
    }
    
    @ApiModelProperty(value = "List of Key/value pairs representing the filled out 'Indicators' survey")
    public List<SurveyData> getIndicatorsSurveyData() {
        return indicatorsSurveyData;
    }

    public void setIndicatorsSurveyData(List<SurveyData> indicatorsSurveyData) {
        this.indicatorsSurveyData = indicatorsSurveyData;
    }
    
    @ApiModelProperty(value = " [ISO 8601](https://es.wikipedia.org/wiki/ISO_8601) formatted creation date")
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    @ApiModelProperty(value = "Number of indicators with red value")
    public Integer getCountRedIndicators() {
        return countRedIndicators;
    }

    public void setCountRedIndicators(Integer countRedIndicators) {
        this.countRedIndicators = countRedIndicators;
    }
    
    @ApiModelProperty(value = "Number of indicators with yellow value")
    public Integer getCountYellowIndicators() {
        return countYellowIndicators;
    }

    public void setCountYellowIndicators(Integer countYellowIndicators) {
        this.countYellowIndicators = countYellowIndicators;
    }
    
    @ApiModelProperty(value = "Number of indicators with green value")
    public Integer getCountGreenIndicators() {
        return countGreenIndicators;
    }

    public void setCountGreenIndicators(Integer countGreenIndicators) {
        this.countGreenIndicators = countGreenIndicators;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Snapshot Indicators {\n");
        sb.append("    familyData: ").append(toIndentedString(familyData)).append("\n");
        sb.append("    indicatorsSurveyData: ").append(toIndentedString(indicatorsSurveyData)).append("\n");
        sb.append("    countRedIndicators:   ").append(toIndentedString(countRedIndicators)).append("\n");
        sb.append("    countYellowIndicators:   ").append(toIndentedString(countYellowIndicators)).append("\n");
        sb.append("    countGreenIndicators:   ").append(toIndentedString(countGreenIndicators)).append("\n");
        sb.append("}");
        return sb.toString();
    }
    
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
    
    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(familyData, indicatorsSurveyData);
    }
    
}
