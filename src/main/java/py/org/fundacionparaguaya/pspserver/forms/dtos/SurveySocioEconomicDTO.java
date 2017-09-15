package py.org.fundacionparaguaya.pspserver.forms.dtos;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import py.org.fundacionparaguaya.pspserver.forms.entities.OdkTableReference;

import java.util.List;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public class SurveySocioEconomicDTO {

    private final OdkTableReference odkTableReference;

    private final List<SurveyIndicatorDTO> indicators;

    private String zona;

    private String acteconomicaPrimaria;

    private String actEconomicaSecundaria;

    private Double salarioMensual;

    SurveySocioEconomicDTO(String zona, String acteconomicaPrimaria, String actEconomicaSecundaria, Double salarioMensual, OdkTableReference odkTableReference, List<SurveyIndicatorDTO> indicators) {
        this.zona = zona;
        this.acteconomicaPrimaria = acteconomicaPrimaria;
        this.actEconomicaSecundaria = actEconomicaSecundaria;
        this.salarioMensual = salarioMensual;
        this.odkTableReference = odkTableReference;
        this.indicators = indicators;
    }

    public static SurveySocioEconomicDTOBuilder builder() {
        return new SurveySocioEconomicDTOBuilder();
    }

    public List<SurveyIndicatorDTO> getIndicators() {
        return indicators;
    }

    public OdkTableReference getOdkTableReference() {
        return odkTableReference;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveySocioEconomicDTO that = (SurveySocioEconomicDTO) o;

        return Objects.equal(this.odkTableReference, that.odkTableReference) &&
                Objects.equal(this.indicators, that.indicators) &&
                Objects.equal(this.zona, that.zona) &&
                Objects.equal(this.acteconomicaPrimaria, that.acteconomicaPrimaria) &&
                Objects.equal(this.actEconomicaSecundaria, that.actEconomicaSecundaria) &&
                Objects.equal(this.salarioMensual, that.salarioMensual);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(odkTableReference, indicators, zona, acteconomicaPrimaria, actEconomicaSecundaria, salarioMensual);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("odkTableReference", odkTableReference)
                .add("indicators", indicators)
                .add("zona", zona)
                .add("acteconomicaPrimaria", acteconomicaPrimaria)
                .add("actEconomicaSecundaria", actEconomicaSecundaria)
                .add("salarioMensual", salarioMensual)
                .toString();
    }
}
