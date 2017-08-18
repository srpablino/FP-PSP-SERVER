package py.org.fundacionparaguaya.pspserver.families.family.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.families.family.domain.Family;

import java.lang.Long;

public interface FamilyRepository extends JpaRepository<Family, Long> {

}
