package py.org.fundacionparaguaya.pspserver.network.userapplication.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.network.userapplication.dto.UserApplicationDTO;

public interface UserApplicationService extends BaseService {

	ResponseEntity<Void> updateUserApplication(UserApplicationDTO userApplicationEntityDTO);

	ResponseEntity<UserApplicationDTO> addUserApplication(UserApplicationDTO userApplicationEntityDTO);
	
	ResponseEntity<UserApplicationDTO> getUserApplicationById(Long userApplicationId);
	
	ResponseEntity<List<UserApplicationDTO>> getAllUserApplications();
	
	ResponseEntity<Void> deleteUserApplication(Long userApplicationId);
	
}
