package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;

public interface SnapshotIndicatorRepository
        extends JpaRepository<SnapshotIndicatorEntity, Long>, JpaSpecificationExecutor<SnapshotIndicatorEntity> {

}
