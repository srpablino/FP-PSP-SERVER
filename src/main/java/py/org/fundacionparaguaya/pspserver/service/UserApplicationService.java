package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.UserApplicationDTO;

public interface UserApplicationService extends BaseService {

	ResponseEntity<Void> updateUserApplication(UserApplicationDTO userApplicationEntityDTO);

	ResponseEntity<UserApplicationDTO> addUserApplication(UserApplicationDTO userApplicationEntityDTO);
	
	ResponseEntity<UserApplicationDTO> getUserApplicationById(Long userApplicationId);
	
	ResponseEntity<List<UserApplicationDTO>> getAllUserApplications();
	
	ResponseEntity<Void> deleteUserApplication(Long userApplicationId);
	
}
