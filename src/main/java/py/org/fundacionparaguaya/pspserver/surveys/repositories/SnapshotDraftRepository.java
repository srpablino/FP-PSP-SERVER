package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotDraftEntity;

/**
 *
 * @author mgonzalez
 *
 */
public interface SnapshotDraftRepository extends
    JpaRepository<SnapshotDraftEntity, Long> {

  List<SnapshotDraftEntity> findByUserId(Long userId);

}