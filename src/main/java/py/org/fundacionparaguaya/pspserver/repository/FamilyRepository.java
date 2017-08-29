package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.domain.FamilyEntity;

import java.lang.Long;

public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {

}
