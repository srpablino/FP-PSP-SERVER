package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.FamilyDTO;

public interface FamilyService {

	FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO);

	FamilyDTO addFamily(FamilyDTO familyDTO);
	
	FamilyDTO getFamilyById(Long familyId);
	
	List<FamilyDTO> getAllFamilies();
	
	void deleteFamily(Long familyId);
	
}
