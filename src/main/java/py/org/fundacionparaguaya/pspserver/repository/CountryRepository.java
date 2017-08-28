package py.org.fundacionparaguaya.pspserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.domain.CountryEntity;

import java.lang.Long;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

}
