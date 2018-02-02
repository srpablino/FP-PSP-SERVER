package py.org.fundacionparaguaya.pspserver.surveys.specifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;

/**
 * @author mcespedes
 *
 */
public class SnapshotEconomicSpecification {
   private SnapshotEconomicSpecification(){
      //not called
   }

    public static Specification<SnapshotEconomicEntity> byFilter(
                    LocalDateTime startDate, LocalDateTime endDate,
                    List<FamilyEntity> families) {
        return new Specification<SnapshotEconomicEntity>() {
            @Override
            public Predicate toPredicate(Root<SnapshotEconomicEntity> root,
                            CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (startDate != null) {
                    predicates.add(cb.between(root.get("createdAt"), startDate,
                                    endDate));
                }
                return cb.and(predicates
                                .toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
