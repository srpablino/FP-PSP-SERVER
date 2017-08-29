package py.org.fundacionparaguaya.pspserver.network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;

import java.lang.Long;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

}
