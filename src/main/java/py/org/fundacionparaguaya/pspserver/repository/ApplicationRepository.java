package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.domain.ApplicationEntity;

/**
 * Created by rodrigovillalba on 8/27/17.
 */
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
}
