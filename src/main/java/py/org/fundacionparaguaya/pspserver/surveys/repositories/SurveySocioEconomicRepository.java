package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;

/**
 * Created by rodrigovillalba on 9/15/17.
 */
public interface SurveySocioEconomicRepository extends JpaRepository<SnapshotEconomicEntity, Long> {
}
