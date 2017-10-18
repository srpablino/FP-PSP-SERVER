package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;

import java.util.List;

/**
 * Created by rodrigovillalba on 10/17/17.
 */
public interface SnapshotEconomicRepository extends JpaRepository<SnapshotEconomicEntity, Long> {

    List<SnapshotEconomicEntity> findBySurveyDefinitionId(Long surveyId);
}
