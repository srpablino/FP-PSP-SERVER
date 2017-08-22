package py.org.fundacionparaguaya.pspserver.network.userapplication.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.network.userapplication.domain.UserApplicationEntityDTO;

public interface UserApplicationService extends BaseService  {

	ResponseEntity<Void> updateUserApplication(UserApplicationEntityDTO userApplicationEntityDTO);

	ResponseEntity<UserApplicationEntityDTO> addUserApplication(UserApplicationEntityDTO userApplicationEntityDTO);
	
	ResponseEntity<UserApplicationEntityDTO> getUserApplicationById(Long userApplicationId);
	
	ResponseEntity<List<UserApplicationEntityDTO>> getAllUserApplications();
	
	ResponseEntity<Void> deleteUserApplication(Long userApplicationId);
	
}
