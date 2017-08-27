package py.org.fundacionparaguaya.pspserver.system.city.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.system.city.domain.CityEntity;

import java.lang.Long;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

}
