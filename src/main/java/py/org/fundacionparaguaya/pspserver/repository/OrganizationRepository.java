package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.domain.OrganizationEntity;

import java.lang.Long;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

}
