package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.surveys.entities.PropertyAttributeEntity;

import java.util.Optional;

/**
 * Created by rodrigovillalba on 10/20/17.
 */
public interface SnapshotPropertyAttributeRepository extends JpaRepository<PropertyAttributeEntity, Long>{

}
