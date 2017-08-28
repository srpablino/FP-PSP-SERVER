package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.domain.CityEntity;

import java.lang.Long;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

}
