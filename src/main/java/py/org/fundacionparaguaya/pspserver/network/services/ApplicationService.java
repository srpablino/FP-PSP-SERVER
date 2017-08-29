package py.org.fundacionparaguaya.pspserver.network.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;

public interface ApplicationService extends BaseService {

	ResponseEntity<Void> updateApplication(ApplicationDTO applicationEntityDTO);

	ResponseEntity<ApplicationDTO> addApplication(ApplicationDTO applicationEntityDTO);
	
	ResponseEntity<ApplicationDTO> getApplicationById(Long applicationId);
	
	ResponseEntity<List<ApplicationDTO>> getAllApplications();
	
	ResponseEntity<Void> deleteApplication(Long applicationId);	
	
}
