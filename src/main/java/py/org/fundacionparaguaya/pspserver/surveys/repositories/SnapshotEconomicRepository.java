package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;

/**
 * Created by rodrigovillalba on 10/19/17.
 */
public interface SnapshotEconomicRepository extends JpaRepository<SnapshotEconomicEntity, Long> {
    Collection<SnapshotEconomicEntity> findBySurveyDefinitionId(Long surveyId);
    List<SnapshotEconomicEntity> findByFamilyFamilyId(Long familiyId);
}
