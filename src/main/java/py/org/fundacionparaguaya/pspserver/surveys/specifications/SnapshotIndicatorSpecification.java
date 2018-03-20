package py.org.fundacionparaguaya.pspserver.surveys.specifications;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity_;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;

/**
 *
 * @author mgonzalez
 *
 */
public class SnapshotIndicatorSpecification {
    
    private static final String ID_ATTRIBUTE = "id";
    
    private static final String ID_FAMILY = "familyId";
    
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    
    public static Specification<SnapshotIndicatorEntity> byCreatedAt(String dateFrom, String dateTo) {
        return new Specification<SnapshotIndicatorEntity>() {
            @Override
            public Predicate toPredicate(Root<SnapshotIndicatorEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (dateFrom != null && dateTo != null) {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

                    Subquery<SnapshotEconomicEntity> subquery = query.subquery(SnapshotEconomicEntity.class);
                    Root<SnapshotEconomicEntity> fromSnapshotEconomic = subquery.from(SnapshotEconomicEntity.class);
                    subquery.select(fromSnapshotEconomic.get(SnapshotEconomicEntity_.getSnapshotIndicator()).get(ID_ATTRIBUTE));

                    predicates.add(cb.greaterThanOrEqualTo(fromSnapshotEconomic.get(SnapshotEconomicEntity_.getCreatedAt()),
                            LocalDateTime.parse(dateFrom, formatter)));
                    predicates.add(cb.lessThan(fromSnapshotEconomic.get(SnapshotEconomicEntity_.getCreatedAt()),
                            LocalDateTime.parse(dateTo, formatter).plusDays(1)));

                    subquery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

                    return cb.in(root.get(ID_ATTRIBUTE)).value(subquery);

                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
    
    public static Specification<SnapshotIndicatorEntity> byFamily(Long familyId) {
        return new Specification<SnapshotIndicatorEntity>() {
            @Override
            public Predicate toPredicate(Root<SnapshotIndicatorEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (familyId!=null) {

                    Subquery<SnapshotEconomicEntity> subquery = query.subquery(SnapshotEconomicEntity.class);
                    Root<SnapshotEconomicEntity> fromSnapshotEconomic = subquery.from(SnapshotEconomicEntity.class);
                    subquery.select(fromSnapshotEconomic.get(SnapshotEconomicEntity_.getSnapshotIndicator()).get(ID_ATTRIBUTE));

                    predicates.add(cb.equal(fromSnapshotEconomic.get(SnapshotEconomicEntity_.getFamily()).get(ID_FAMILY),
                            familyId));

                    subquery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

                    return cb.in(root.get(ID_ATTRIBUTE)).value(subquery);

                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
