package py.org.fundacionparaguaya.pspserver.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolType;
import py.org.fundacionparaguaya.pspserver.security.entities.TermCondPolEntity;
import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolLanguage;

public interface TermCondPolRepository extends
    JpaRepository<TermCondPolEntity, Long> {
    TermCondPolEntity findFirstByTypeCodAndLanguageOrderByCreatedDateDesc(
        TermCondPolType type, TermCondPolLanguage language);
}
