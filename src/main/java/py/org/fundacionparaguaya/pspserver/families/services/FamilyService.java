package py.org.fundacionparaguaya.pspserver.families.services;

import java.util.List;
import java.util.Map;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;

public interface FamilyService {

	FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO);

	FamilyDTO addFamily(FamilyDTO familyDTO);
	
	FamilyDTO getFamilyById(Long familyId);
	
	List<FamilyDTO> getAllFamilies();
	
	void deleteFamily(Long familyId);

	Map<String, Object> getFamiliesByFilter(Long organizationId, Long countryId, Long cityId, String freeText);
	
}
