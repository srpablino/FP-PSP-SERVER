package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;

/**
 * Created by rodrigovillalba on 10/17/17.
 */
public interface SnapshotIndicatorRepository extends JpaRepository<SnapshotIndicatorEntity, Long> {
}
