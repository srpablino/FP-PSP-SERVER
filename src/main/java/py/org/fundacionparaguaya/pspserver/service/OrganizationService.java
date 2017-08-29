package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.OrganizationDTO;

public interface OrganizationService extends BaseService {

	ResponseEntity<Void> updateOrganization(OrganizationDTO organizationEntityDTO);

	ResponseEntity<OrganizationDTO> addOrganization(OrganizationDTO organizationEntityDTO);
	
	ResponseEntity<OrganizationDTO> getOrganizationById(Long organizationId);
	
	ResponseEntity<List<OrganizationDTO>> getAllOrganizations();
	
	ResponseEntity<Void> deleteOrganization(Long organizationId);
	
}
