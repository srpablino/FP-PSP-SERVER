package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.ApplicationDTO;

public interface ApplicationService {

	ApplicationDTO updateApplication(Long applicationId, ApplicationDTO application);

	ApplicationDTO addApplication(ApplicationDTO application);
	
	ApplicationDTO getApplicationById(Long applicationId);
	
	List<ApplicationDTO> getAllApplications();
	
	void deleteApplication(Long applicationId);
	
}
