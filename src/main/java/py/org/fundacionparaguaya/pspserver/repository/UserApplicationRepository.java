package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.domain.UserApplicationEntity;

import java.lang.Long;

public interface UserApplicationRepository extends JpaRepository<UserApplicationEntity, Long> {

}
