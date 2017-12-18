package py.org.fundacionparaguaya.pspserver.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

import java.lang.Long;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
	Optional<CountryEntity> findByAlfa2Code(String alfa3code);
}
