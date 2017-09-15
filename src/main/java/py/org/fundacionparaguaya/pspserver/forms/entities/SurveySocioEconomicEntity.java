package py.org.fundacionparaguaya.pspserver.forms.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Entity
@Table(schema="data_collect", name = "survey_socioeconomics")
public class SurveySocioEconomicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "encuesta_semaforo_id")
    private Long encuestaSemaforoId;

    @Size(max = 200)
    @Column(name = "zona")
    private String zona;

    @Size(max = 200)
    @Column(name = "acteconomica_primaria")
    private String acteconomicaPrimaria;

    @Size(max = 2147483647)
    @Column(name = "acteconomica_secundaria")
    private String actEconocmicaSecundaria;

    @Column(name = "salario_mensual")
    private Double salarioMensual;

    @OneToOne
    @JoinColumn(name = "survey_indicator_id")
    private SurveyIndicatorEntity surveyIndicator;

    public Long getEncuestaSemaforoId() {
        return encuestaSemaforoId;
    }

    public void setEncuestaSemaforoId(Long encuestaSemaforoId) {
        this.encuestaSemaforoId = encuestaSemaforoId;
    }

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

    public String getActEconocmicaSecundaria() {
        return actEconocmicaSecundaria;
    }

    public void setActEconocmicaSecundaria(String actEconocmicaSecundaria) {
        this.actEconocmicaSecundaria = actEconocmicaSecundaria;
    }

    public Double getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(Double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }


    public SurveyIndicatorEntity getSurveyIndicator() {
        return surveyIndicator;
    }

    public void setSurveyIndicator(SurveyIndicatorEntity surveyIndicator) {
        this.surveyIndicator = surveyIndicator;
    }
}
