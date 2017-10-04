package py.org.fundacionparaguaya.pspserver.forms.dtos;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/18/17.
 */
public class SurveyQueryDTO {

    private final List<SurveyIndicatorDTO> indicators;

    private final OdkRowReferenceDTO odkRowReferenceDTO;

    private final SurveySocioEconomicQueryDTO socioEconomic;

    private SurveyQueryDTO(List<SurveyIndicatorDTO> indicators, OdkRowReferenceDTO odkRowReferenceDTO, SurveySocioEconomicQueryDTO socioEconomic) {
        this.indicators = indicators;
        this.odkRowReferenceDTO = odkRowReferenceDTO;
        this.socioEconomic = socioEconomic;
    }

    public static SurveyQueryDTO of(List<SurveyIndicatorDTO> indicators, SurveySocioEconomicQueryDTO socioEconomic) {
        return new SurveyQueryDTO(indicators, null, socioEconomic);
    }

    public static SurveyQueryDTO of(OdkRowReferenceDTO odkRowReferenceDTO, SurveySocioEconomicQueryDTO socioEconomic) {
        checkNotNull(odkRowReferenceDTO);
        checkNotNull(socioEconomic);
        return new SurveyQueryDTO(null, odkRowReferenceDTO, socioEconomic);
    }
    public List<SurveyIndicatorDTO> getIndicators() {
        return indicators;
    }

    public OdkRowReferenceDTO getOdkRowReferenceDTO() {
        return odkRowReferenceDTO;
    }

    public SurveySocioEconomicQueryDTO getSocioEconomic() {
        return socioEconomic;
    }


}
