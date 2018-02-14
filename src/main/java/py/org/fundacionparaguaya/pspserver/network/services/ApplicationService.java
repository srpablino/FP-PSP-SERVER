package py.org.fundacionparaguaya.pspserver.network.services;

import py.org.fundacionparaguaya.pspserver.common.pagination.PaginableList;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;

import java.util.List;

public interface ApplicationService {

    ApplicationDTO updateApplication(Long applicationId,
                                     ApplicationDTO application);

    ApplicationDTO addApplication(ApplicationDTO application);

    ApplicationDTO getApplicationById(Long applicationId);

    List<ApplicationDTO> getAllApplications();

    List<ApplicationDTO> getAllHubs();

    List<ApplicationDTO> getAllPartners();

    void deleteApplication(Long applicationId);

    ApplicationDTO getApplicationDashboard(Long applicationId,
                                           UserDetailsDTO details);

    PaginableList<ApplicationDTO> listApplicationsHubs(int page, int perPage,
            String orderBy, String sortBy);
}
