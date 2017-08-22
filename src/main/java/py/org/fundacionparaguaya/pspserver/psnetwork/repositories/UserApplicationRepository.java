package py.org.fundacionparaguaya.pspserver.psnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.psnetwork.entities.UserApplicationEntity;

import java.lang.Long;

public interface UserApplicationRepository extends JpaRepository<UserApplicationEntity, Long> {

}
