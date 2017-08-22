package py.org.fundacionparaguaya.pspserver.network.application.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.network.application.domain.ApplicationEntityDTO;

public interface ApplicationService extends BaseService{

	ResponseEntity<Void> updateApplication(ApplicationEntityDTO applicationEntityDTO);

	ResponseEntity<ApplicationEntityDTO> addApplication(ApplicationEntityDTO applicationEntityDTO);
	
	ResponseEntity<ApplicationEntityDTO> getApplicationById(Long applicationId);
	
	ResponseEntity<List<ApplicationEntityDTO>> getAllApplications();
	
	ResponseEntity<Void> deleteApplication(Long applicationId);	
	
}
