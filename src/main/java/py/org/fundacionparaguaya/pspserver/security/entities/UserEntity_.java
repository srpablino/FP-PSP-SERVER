package py.org.fundacionparaguaya.pspserver.security.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserEntity.class)
public class UserEntity_ {

    private UserEntity_() {}

    public static volatile SingularAttribute<UserEntity, Long> id;

    public static volatile SingularAttribute<UserEntity, String> username;

    public static volatile SingularAttribute<UserEntity, String> email;

    public static volatile SingularAttribute<UserEntity, String> pass;

    public static volatile SingularAttribute<UserEntity, Boolean> active;
}
