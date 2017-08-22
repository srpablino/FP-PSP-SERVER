package py.org.fundacionparaguaya.pspserver.network.userapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.network.userapplication.domain.UserApplicationEntity;

import java.lang.Long;

public interface UserApplicationRepository extends JpaRepository<UserApplicationEntity, Long> {

}
