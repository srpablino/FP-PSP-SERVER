package py.org.fundacionparaguaya.pspserver.families.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;

public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {
	
	Page<FamilyEntity> findAll(Pageable page);
	
	List<FamilyEntity> findByOrganizationIdAndCountryIdAndCityIdAndNameContainingIgnoreCase
	(Long organizationId, Long countryId, Long cityId, String freeText);
	
}
