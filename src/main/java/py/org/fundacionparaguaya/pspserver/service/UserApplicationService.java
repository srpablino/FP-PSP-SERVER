package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.UserApplicationDTO;

public interface UserApplicationService {

	UserApplicationDTO updateUserApplication(Long userApplicationId, UserApplicationDTO applicationDTO);

	UserApplicationDTO addUserApplication(UserApplicationDTO applicationDTO);
	
	UserApplicationDTO getUserApplicationById(Long userApplicationId);
	
	List<UserApplicationDTO> getAllUserApplications();
	
	void deleteUserApplication(Long userApplicationId);
	
}
