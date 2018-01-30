package py.org.fundacionparaguaya.pspserver.surveys.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;

/**
 * @author mcespedes
 *
 */
@StaticMetamodel(SnapshotDraftEntity.class)
public class SnapshotDraftEntity_ {
    public static volatile SingularAttribute<SnapshotDraftEntity, UserEntity> user;
    public static volatile SingularAttribute<SnapshotDraftEntity, String> personFirstName;
    public static volatile SingularAttribute<SnapshotDraftEntity, String> personLastName;
}
