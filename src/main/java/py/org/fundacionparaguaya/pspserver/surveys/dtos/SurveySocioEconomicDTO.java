package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public class SurveySocioEconomicDTO {

    private OdkRowReferenceDTO odkRowReferenceDTO;

    private List<SurveyIndicatorDTO> indicators;

    @JsonIgnore
    private Long surveyId;

    private String zona;

    private String acteconomicaPrimaria;

    private String actEconomicaSecundaria;

    private Double salarioMensual;


    public SurveySocioEconomicDTO() {

    }

    SurveySocioEconomicDTO(String zona, String acteconomicaPrimaria, String actEconomicaSecundaria, Double salarioMensual, OdkRowReferenceDTO odkRowReferenceDTO, List<SurveyIndicatorDTO> indicators) {
        this.zona = zona;
        this.acteconomicaPrimaria = acteconomicaPrimaria;
        this.actEconomicaSecundaria = actEconomicaSecundaria;
        this.salarioMensual = salarioMensual;
        this.odkRowReferenceDTO = odkRowReferenceDTO;
        this.indicators = indicators;
    }

    public static SurveySocioEconomicDTOBuilder builder() {
        return new SurveySocioEconomicDTOBuilder();
    }

    public List<SurveyIndicatorDTO> getIndicators() {
        return indicators;
    }

    public OdkRowReferenceDTO getOdkRowReferenceDTO() {
        return odkRowReferenceDTO;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveySocioEconomicDTO that = (SurveySocioEconomicDTO) o;

        return Objects.equal(this.odkRowReferenceDTO, that.odkRowReferenceDTO) &&
                Objects.equal(this.indicators, that.indicators) &&
                Objects.equal(this.zona, that.zona) &&
                Objects.equal(this.acteconomicaPrimaria, that.acteconomicaPrimaria) &&
                Objects.equal(this.actEconomicaSecundaria, that.actEconomicaSecundaria) &&
                Objects.equal(this.salarioMensual, that.salarioMensual);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(odkRowReferenceDTO, indicators, zona, acteconomicaPrimaria, actEconomicaSecundaria, salarioMensual);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("odkRowReferenceDTO", odkRowReferenceDTO)
                .add("indicators", indicators)
                .add("zona", zona)
                .add("acteconomicaPrimaria", acteconomicaPrimaria)
                .add("actEconomicaSecundaria", actEconomicaSecundaria)
                .add("salarioMensual", salarioMensual)
                .toString();
    }

    public Double getSalarioMensual() {
        return salarioMensual;
    }

    public Long getSurveyId() {
        return surveyId;
    }
}
