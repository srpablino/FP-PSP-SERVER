package py.org.fundacionparaguaya.pspserver.psfamilies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.psfamilies.entities.PersonEntity;

import java.lang.Long;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
