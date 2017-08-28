package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.ApplicationDTO;

public interface ApplicationService extends BaseService {

	ResponseEntity<Void> updateApplication(ApplicationDTO applicationEntityDTO);

	ResponseEntity<ApplicationDTO> addApplication(ApplicationDTO applicationEntityDTO);
	
	ResponseEntity<ApplicationDTO> getApplicationById(Long applicationId);
	
	ResponseEntity<List<ApplicationDTO>> getAllApplications();
	
	ResponseEntity<Void> deleteApplication(Long applicationId);	
	
}
