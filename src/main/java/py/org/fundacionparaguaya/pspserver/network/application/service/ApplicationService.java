package py.org.fundacionparaguaya.pspserver.network.application.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.network.application.dto.ApplicationDTO;

public interface ApplicationService extends BaseService {

	ResponseEntity<Void> updateApplication(ApplicationDTO applicationEntityDTO);

	ResponseEntity<ApplicationDTO> addApplication(ApplicationDTO applicationEntityDTO);
	
	ResponseEntity<ApplicationDTO> getApplicationById(Long applicationId);
	
	ResponseEntity<List<ApplicationDTO>> getAllApplications();
	
	ResponseEntity<Void> deleteApplication(Long applicationId);	
	
}
