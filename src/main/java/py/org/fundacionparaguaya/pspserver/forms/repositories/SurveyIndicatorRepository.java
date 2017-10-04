package py.org.fundacionparaguaya.pspserver.forms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.forms.entities.OdkRowReferenceEntity;
/**
 * Created by rodrigovillalba on 9/25/17.
 */
public interface SurveyIndicatorRepository extends JpaRepository<OdkRowReferenceEntity, Long> {
}
