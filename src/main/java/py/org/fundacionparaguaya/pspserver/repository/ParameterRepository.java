package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.domain.ParameterEntity;

import java.lang.Long;

public interface ParameterRepository extends JpaRepository<ParameterEntity, Long> {

}
