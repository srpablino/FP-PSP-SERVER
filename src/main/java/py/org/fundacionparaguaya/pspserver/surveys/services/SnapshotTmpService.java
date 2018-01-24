package py.org.fundacionparaguaya.pspserver.surveys.services;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotTmp;
/**
 *
 * @author mgonzalez
 *
 */
public interface SnapshotTmpService {

    SnapshotTmp addSnapshotTmp(SnapshotTmp snapshot);

    SnapshotTmp getSnapshotTmp(Long id);
    
    SnapshotTmp updateSnapshotTmp(SnapshotTmp snapshotTmp);

    void deleteSnapshotTmp(Long id);
}
