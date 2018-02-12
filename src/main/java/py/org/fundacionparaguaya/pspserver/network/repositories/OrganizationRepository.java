package py.org.fundacionparaguaya.pspserver.network.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;

import java.util.List;

public interface OrganizationRepository
                extends PagingAndSortingRepository<OrganizationEntity, Long>,
                        JpaSpecificationExecutor<OrganizationEntity> {

    OrganizationEntity findById(Long id);

    List<OrganizationEntity> findAll();

    Page<OrganizationEntity> findAll(Pageable page);

    List<OrganizationEntity> findByApplicationIdAndIsActive(Long applicationId,
                                                            boolean isActive);
}
