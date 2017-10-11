package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.surveys.entities.OdkRowReferenceEntity;
/**
 * Created by rodrigovillalba on 9/25/17.
 */
public interface SurveyIndicatorRepository extends JpaRepository<OdkRowReferenceEntity, Long> {
}
