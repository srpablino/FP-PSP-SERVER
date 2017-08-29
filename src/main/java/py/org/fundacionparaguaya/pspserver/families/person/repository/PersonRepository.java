package py.org.fundacionparaguaya.pspserver.families.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.families.person.domain.PersonEntity;

import java.lang.Long;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
