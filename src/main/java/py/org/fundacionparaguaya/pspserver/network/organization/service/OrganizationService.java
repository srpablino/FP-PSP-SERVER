package py.org.fundacionparaguaya.pspserver.network.organization.service;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.network.organization.dto.OrganizationDTO;

import java.util.List;

public interface OrganizationService extends BaseService {

	ResponseEntity<Void> updateOrganization(OrganizationDTO organizationEntityDTO);

	ResponseEntity<OrganizationDTO> addOrganization(OrganizationDTO organizationEntityDTO);
	
	ResponseEntity<OrganizationDTO> getOrganizationById(Long organizationId);
	
	ResponseEntity<List<OrganizationDTO>> getAllOrganizations();
	
	ResponseEntity<Void> deleteOrganization(Long organizationId);

}
