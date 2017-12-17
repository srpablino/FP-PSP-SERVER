package py.org.fundacionparaguaya.pspserver.families.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;


public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {

}
