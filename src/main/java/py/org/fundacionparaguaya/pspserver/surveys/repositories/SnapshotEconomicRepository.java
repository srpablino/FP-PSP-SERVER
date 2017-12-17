package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;

import java.util.Collection;

/**
 * Created by rodrigovillalba on 10/19/17.
 */
public interface SnapshotEconomicRepository extends JpaRepository<SnapshotEconomicEntity, Long> {
    Collection<SnapshotEconomicEntity> findBySurveyDefinitionId(Long surveyId);
}
