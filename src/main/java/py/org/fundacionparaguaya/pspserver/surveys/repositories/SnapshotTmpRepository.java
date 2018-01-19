package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotTmpEntity;

/**
 *
 * @author mgonzalez
 *
 */
public interface SnapshotTmpRepository extends
    JpaRepository<SnapshotTmpEntity, Long> {

}
