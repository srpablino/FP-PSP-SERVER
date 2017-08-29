package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.FamilyDTO;

public interface FamilyService extends BaseService {

	ResponseEntity<Void> updateFamily(FamilyDTO familyEntityDTO);

	ResponseEntity<FamilyDTO> addFamily(FamilyDTO familyEntityDTO);
	
	ResponseEntity<FamilyDTO> getFamilyById(Long familyId);
	
	ResponseEntity<List<FamilyDTO>> getAllFamilies();
	
	ResponseEntity<Void> deleteFamily(Long familyId);
	
}
