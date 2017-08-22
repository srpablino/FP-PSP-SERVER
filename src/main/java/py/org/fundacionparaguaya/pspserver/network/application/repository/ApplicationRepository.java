package py.org.fundacionparaguaya.pspserver.network.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.network.application.domain.Application;

import java.lang.Long;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
