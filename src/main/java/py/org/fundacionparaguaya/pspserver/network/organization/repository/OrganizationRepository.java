package py.org.fundacionparaguaya.pspserver.network.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.network.organization.domain.Organization;

import java.lang.Long;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
