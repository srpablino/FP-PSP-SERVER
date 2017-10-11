package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import java.util.ArrayList;
import java.util.List;

public class SurveySocioEconomicDTOBuilder {
    private Long surveyId;
    private String zona;
    private String acteconomicaPrimaria;
    private String actEconomicaSegundaria;
    private Double salarioMensual;
    private List<SurveyIndicatorDTO> indicators;
    private OdkRowReferenceDTO odkRowReferenceDTO;

    public SurveySocioEconomicDTOBuilder zona(String zona) {
        this.zona = zona;
        return this;
    }

    public SurveySocioEconomicDTOBuilder acteconomicaPrimaria(String acteconomicaPrimaria) {
        this.acteconomicaPrimaria = acteconomicaPrimaria;
        return this;
    }

    public SurveySocioEconomicDTOBuilder actEconomicaSegundaria(String actEconomicaSegundaria) {
        this.actEconomicaSegundaria = actEconomicaSegundaria;
        return this;
    }

    public SurveySocioEconomicDTOBuilder salarioMensual(Double salarioMensual) {
        this.salarioMensual = salarioMensual;
        return this;
    }


    public SurveySocioEconomicDTOBuilder addIndicator(SurveyIndicatorDTO indicator) {
        if (indicators ==  null) {
            indicators = new ArrayList<>();
        }
        indicators.add(indicator);
        return this;
    }

    public SurveySocioEconomicDTOBuilder odkTableReference(OdkRowReferenceDTO odkRowReferenceDTO) {
        this.odkRowReferenceDTO = odkRowReferenceDTO;
        return this;
    }

    public SurveySocioEconomicDTOBuilder surveyId(Long surveyId) {
        this.surveyId = surveyId;
        return this;
    }

    public SurveySocioEconomicDTO build() {
        return new SurveySocioEconomicDTO(zona, acteconomicaPrimaria, actEconomicaSegundaria, salarioMensual, odkRowReferenceDTO, indicators);
    }

}