package py.org.fundacionparaguaya.pspserver.psnetwork.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.psnetwork.dtos.ApplicationEntityDTO;

public interface ApplicationService extends BaseService{

	ResponseEntity<Void> updateApplication(ApplicationEntityDTO applicationEntityDTO);

	ResponseEntity<ApplicationEntityDTO> addApplication(ApplicationEntityDTO applicationEntityDTO);
	
	ResponseEntity<ApplicationEntityDTO> getApplicationById(Long applicationId);
	
	ResponseEntity<List<ApplicationEntityDTO>> getAllApplications();
	
	ResponseEntity<Void> deleteApplication(Long applicationId);	
	
}
