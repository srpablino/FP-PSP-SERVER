package py.org.fundacionparaguaya.pspserver.families.services;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.PersonEntity;


public interface FamilyService {

	FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO);

	FamilyDTO addFamily(FamilyDTO familyDTO);
	
	FamilyDTO getFamilyById(Long familyId);
	
	List<FamilyDTO> getAllFamilies();
	
	void deleteFamily(Long familyId);
	
	String generateFamilyCode(PersonEntity person);
	
}
