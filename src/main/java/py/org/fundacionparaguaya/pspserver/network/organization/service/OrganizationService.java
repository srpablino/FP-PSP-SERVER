package py.org.fundacionparaguaya.pspserver.network.organization.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.network.organization.domain.OrganizationEntityDTO;

public interface OrganizationService extends BaseService {

	ResponseEntity<Void> updateOrganization(OrganizationEntityDTO organizationEntityDTO);

	ResponseEntity<OrganizationEntityDTO> addOrganization(OrganizationEntityDTO organizationEntityDTO);
	
	ResponseEntity<OrganizationEntityDTO> getOrganizationById(Long organizationId);
	
	ResponseEntity<List<OrganizationEntityDTO>> getAllOrganizations();
	
	ResponseEntity<Void> deleteOrganization(Long organizationId);
	
}
