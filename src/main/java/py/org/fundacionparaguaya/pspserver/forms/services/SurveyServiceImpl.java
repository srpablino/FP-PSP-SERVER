package py.org.fundacionparaguaya.pspserver.forms.services;

import org.opendatakit.aggregate.odktables.rest.entity.RowOutcomeList;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveySocioEconomicDTO;
import py.org.fundacionparaguaya.pspserver.forms.entities.SurveySocioEconomicEntity;
import py.org.fundacionparaguaya.pspserver.forms.repositories.SurveySocioEconomicRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    private final OdkService odkService;

    private final SurveySocioEconomicRepository repository;

    public SurveyServiceImpl(OdkService odkService, SurveySocioEconomicRepository repository) {
        this.odkService = odkService;
        this.repository = repository;
    }

    @Override
    public SurveySocioEconomicDTO addNew(SurveySocioEconomicDTO dto) {
        checkNotNull(dto);
        checkNotNull(dto.getOdkTableReference());

        SurveySocioEconomicEntity entity = new SurveySocioEconomicEntity();
        BeanUtils.copyProperties(dto, entity);

        RowOutcomeList rowOutcomeList = odkService.addNewAnsweredQuestion(dto.getOdkTableReference(), dto.getIndicators());

        repository.save(entity);

        return SurveySocioEconomicDTO.builder()
                .odkTableReference(dto.getOdkTableReference())
                .build();
    }
}
