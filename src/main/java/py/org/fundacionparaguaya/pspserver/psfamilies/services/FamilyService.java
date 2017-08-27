package py.org.fundacionparaguaya.pspserver.psfamilies.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.psfamilies.dtos.FamilyEntityDTO;

public interface FamilyService extends BaseService  {

	ResponseEntity<Void> updateFamily(FamilyEntityDTO familyEntityDTO);

	ResponseEntity<FamilyEntityDTO> addFamily(FamilyEntityDTO familyEntityDTO);
	
	ResponseEntity<FamilyEntityDTO> getFamilyById(Long familyId);
	
	ResponseEntity<List<FamilyEntityDTO>> getAllFamilies();
	
	ResponseEntity<Void> deleteFamily(Long familyId);
	
}
