package py.org.fundacionparaguaya.pspserver.system.parameter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.system.parameter.domain.ParameterEntity;

import java.lang.Long;

public interface ParameterRepository extends JpaRepository<ParameterEntity, Long> {

}
