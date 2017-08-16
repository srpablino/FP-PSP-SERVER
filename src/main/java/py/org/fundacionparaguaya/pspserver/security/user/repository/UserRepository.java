package py.org.fundacionparaguaya.pspserver.security.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.security.user.domain.User;

import java.lang.Long;

public interface UserRepository extends JpaRepository<User, Long> {

}
