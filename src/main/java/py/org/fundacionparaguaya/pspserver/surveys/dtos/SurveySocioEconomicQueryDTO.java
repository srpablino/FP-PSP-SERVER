package py.org.fundacionparaguaya.pspserver.surveys.dtos;

/**
 * Created by rodrigovillalba on 9/26/17.
 */
public class SurveySocioEconomicQueryDTO {

    private String zona;

    private String acteconomicaPrimaria;

    private String actEconomicaSecundaria;

    private Double salarioMensual;

    public SurveySocioEconomicQueryDTO() {}


    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getActeconomicaPrimaria() {
        return acteconomicaPrimaria;
    }

    public void setActeconomicaPrimaria(String acteconomicaPrimaria) {
        this.acteconomicaPrimaria = acteconomicaPrimaria;
    }

    public String getActEconomicaSecundaria() {
        return actEconomicaSecundaria;
    }

    public void setActEconomicaSecundaria(String actEconomicaSecundaria) {
        this.actEconomicaSecundaria = actEconomicaSecundaria;
    }

    public Double getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(Double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }
}
