package py.org.fundacionparaguaya.pspserver.security.userrole.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.security.userrole.domain.UserRoleEntity;

import java.lang.Long;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

}
