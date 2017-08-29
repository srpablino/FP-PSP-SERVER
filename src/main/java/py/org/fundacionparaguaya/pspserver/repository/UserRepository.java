package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.domain.UserEntity;

import java.util.Optional;

/**
 * Created by rodrigovillalba on 8/27/17.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findOneByUsername(String username);
}

