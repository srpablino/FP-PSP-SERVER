package py.org.fundacionparaguaya.pspserver.psnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.psnetwork.entities.ApplicationEntity;

import java.lang.Long;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

}
