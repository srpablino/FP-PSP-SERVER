package py.org.fundacionparaguaya.pspserver.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;

import java.lang.Long;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

}
