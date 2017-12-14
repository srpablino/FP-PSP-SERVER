package py.org.fundacionparaguaya.pspserver.families.services;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;

public interface FamilyService {

	FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO);

	FamilyDTO addFamily(FamilyDTO familyDTO);
	
	FamilyDTO getFamilyById(Long familyId);
	
	List<FamilyDTO> getAllFamilies();
	
	void deleteFamily(Long familyId);

	List<SurveyData> getFamiliesByFilter(Long organizationId, Long countryId, Long cityId, String freeText);
	
}
