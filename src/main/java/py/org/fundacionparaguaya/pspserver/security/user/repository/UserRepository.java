package py.org.fundacionparaguaya.pspserver.security.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.security.user.domain.UserEntity;

import java.util.Optional;

/**
 * Created by rodrigovillalba on 8/27/17.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findOneByUsername(String username);
}

