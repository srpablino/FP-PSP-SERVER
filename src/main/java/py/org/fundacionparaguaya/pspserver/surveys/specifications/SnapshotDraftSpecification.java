package py.org.fundacionparaguaya.pspserver.surveys.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotDraftEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotDraftEntity_;

/**
 * @author mcespedes
 *
 */
public class SnapshotDraftSpecification {

    private static final String ID_ATTRIBUTE = "id";

    public static Specification<SnapshotDraftEntity> byFilter(Long userId,
                    String description) {
        return new Specification<SnapshotDraftEntity>() {
            @Override
            public Predicate toPredicate(Root<SnapshotDraftEntity> root,
                            CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                if (userId != null) {
                    Join<SnapshotDraftEntity, UserEntity> joinUser = root
                                    .join(SnapshotDraftEntity_.user);
                    Expression<Long> byUserId = joinUser
                                    .<Long>get(ID_ATTRIBUTE);
                    predicates.add(cb.equal(byUserId, userId));
                }

                if (description != null) {

                    Predicate orClause = cb.or(cb.like(
                        cb.upper(root.<String>get(
                           SnapshotDraftEntity_.personFirstName)),
                           "%" + description.trim().toUpperCase()
                           + "%"),cb.like(cb.upper(root
                           .<String>get(SnapshotDraftEntity_.personLastName)),
                           "%" + description.trim().toUpperCase() + "%"));

                    predicates.add(orClause);
                }

                return cb.and(predicates
                                .toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
