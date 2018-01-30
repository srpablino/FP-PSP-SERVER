package py.org.fundacionparaguaya.pspserver.network.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;

public interface OrganizationRepository extends PagingAndSortingRepository<OrganizationEntity, Long>, JpaSpecificationExecutor<OrganizationEntity>{
	Optional<OrganizationEntity> findOneByName(String name);
	List<OrganizationEntity> findAll();
	Page<OrganizationEntity> findAll(Pageable page);
}
