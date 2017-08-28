package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.domain.UserRoleEntity;

/**
 * Created by rodrigovillalba on 8/27/17.
 */
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

}