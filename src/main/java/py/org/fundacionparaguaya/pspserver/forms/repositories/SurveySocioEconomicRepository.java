package py.org.fundacionparaguaya.pspserver.forms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.forms.entities.SurveySocioEconomicEntity;

import java.util.List;

/**
 * Created by rodrigovillalba on 9/15/17.
 */
public interface SurveySocioEconomicRepository extends JpaRepository<SurveySocioEconomicEntity, Long> {
    List<SurveySocioEconomicEntity> findBySalarioMensual(Double salarioMensual);
}
