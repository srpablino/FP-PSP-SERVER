package py.org.fundacionparaguaya.pspserver.network.services;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;

import java.util.List;

public interface OrganizationService extends BaseService {

	ResponseEntity<Void> updateOrganization(OrganizationDTO organizationEntityDTO);

	ResponseEntity<OrganizationDTO> addOrganization(OrganizationDTO organizationEntityDTO);
	
	ResponseEntity<OrganizationDTO> getOrganizationById(Long organizationId);
	
	ResponseEntity<List<OrganizationDTO>> getAllOrganizations();
	
	ResponseEntity<Void> deleteOrganization(Long organizationId);

}
