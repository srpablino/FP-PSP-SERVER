package py.org.fundacionparaguaya.pspserver.psnetwork.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.psnetwork.dtos.UserApplicationEntityDTO;

public interface UserApplicationService extends BaseService  {

	ResponseEntity<Void> updateUserApplication(UserApplicationEntityDTO userApplicationEntityDTO);

	ResponseEntity<UserApplicationEntityDTO> addUserApplication(UserApplicationEntityDTO userApplicationEntityDTO);
	
	ResponseEntity<UserApplicationEntityDTO> getUserApplicationById(Long userApplicationId);
	
	ResponseEntity<List<UserApplicationEntityDTO>> getAllUserApplications();
	
	ResponseEntity<Void> deleteUserApplication(Long userApplicationId);
	
}
