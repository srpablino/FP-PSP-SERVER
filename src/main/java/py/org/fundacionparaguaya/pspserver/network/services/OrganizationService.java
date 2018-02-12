package py.org.fundacionparaguaya.pspserver.network.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;

import java.util.List;

public interface OrganizationService {

    OrganizationDTO updateOrganization(Long organizationId,
                                       OrganizationDTO organizationDTO);

    OrganizationDTO addOrganization(OrganizationDTO organizationDTO);

    OrganizationDTO getOrganizationById(Long organizationId);

    List<OrganizationDTO> getAllOrganizations();

    List<OrganizationDTO> getOrganizationsByApplicationId(Long applicationId);

    void deleteOrganization(Long organizationId);

    Page<OrganizationDTO> listOrganizations(PageRequest pageRequest,
                                            UserDetailsDTO userDetails);

    OrganizationDTO getOrganizationDashboard(Long organizationId,
                                             UserDetailsDTO details);
}
