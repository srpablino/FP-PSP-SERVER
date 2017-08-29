package py.org.fundacionparaguaya.pspserver.network.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.network.dtos.UserApplicationDTO;

public interface UserApplicationService extends BaseService {

	ResponseEntity<Void> updateUserApplication(UserApplicationDTO userApplicationEntityDTO);

	ResponseEntity<UserApplicationDTO> addUserApplication(UserApplicationDTO userApplicationEntityDTO);
	
	ResponseEntity<UserApplicationDTO> getUserApplicationById(Long userApplicationId);
	
	ResponseEntity<List<UserApplicationDTO>> getAllUserApplications();
	
	ResponseEntity<Void> deleteUserApplication(Long userApplicationId);
	
}
