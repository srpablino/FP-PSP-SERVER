package py.org.fundacionparaguaya.pspserver.network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.network.entities.UserApplicationEntity;

import java.lang.Long;

public interface UserApplicationRepository extends JpaRepository<UserApplicationEntity, Long> {

}
