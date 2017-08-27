package py.org.fundacionparaguaya.pspserver.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;

import java.lang.Long;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
