package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.OrganizationDTO;

public interface OrganizationService{

	OrganizationDTO updateOrganization(Long organizationId, OrganizationDTO organizationDTO);

	OrganizationDTO addOrganization(OrganizationDTO organizationDTO);
	
	OrganizationDTO getOrganizationById(Long organizationId);
	
	List<OrganizationDTO> getAllOrganizations();
	
	void deleteOrganization(Long organizationId);
	
}
