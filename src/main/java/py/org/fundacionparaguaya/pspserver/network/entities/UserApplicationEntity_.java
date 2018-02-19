package py.org.fundacionparaguaya.pspserver.network.entities;

import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserApplicationEntity.class)
public class UserApplicationEntity_ {

    private UserApplicationEntity_() {}

    public static volatile SingularAttribute<UserApplicationEntity, Long> id;

    public static volatile SingularAttribute<UserApplicationEntity, UserEntity> user;

    public static volatile SingularAttribute<UserApplicationEntity, ApplicationEntity> application;

    public static volatile SingularAttribute<UserApplicationEntity, OrganizationEntity> organization;
}
