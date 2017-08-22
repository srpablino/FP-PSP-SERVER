package py.org.fundacionparaguaya.pspserver.system.city.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.system.city.domain.City;

import java.lang.Long;

public interface CityRepository extends JpaRepository<City, Long> {

}
