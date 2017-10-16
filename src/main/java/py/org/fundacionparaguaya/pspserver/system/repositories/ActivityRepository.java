package py.org.fundacionparaguaya.pspserver.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.system.entities.ActivityEntity;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
}
