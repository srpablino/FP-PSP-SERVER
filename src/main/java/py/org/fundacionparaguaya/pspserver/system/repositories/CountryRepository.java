package py.org.fundacionparaguaya.pspserver.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

import java.lang.Long;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

}
