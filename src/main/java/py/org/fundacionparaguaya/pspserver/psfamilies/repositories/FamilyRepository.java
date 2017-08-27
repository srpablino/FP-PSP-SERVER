package py.org.fundacionparaguaya.pspserver.psfamilies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.psfamilies.entities.FamilyEntity;

import java.lang.Long;

public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {

}
