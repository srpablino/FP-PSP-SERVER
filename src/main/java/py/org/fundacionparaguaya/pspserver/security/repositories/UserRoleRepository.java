package py.org.fundacionparaguaya.pspserver.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.security.entities.UserRoleEntity;

import java.lang.Long;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

}
