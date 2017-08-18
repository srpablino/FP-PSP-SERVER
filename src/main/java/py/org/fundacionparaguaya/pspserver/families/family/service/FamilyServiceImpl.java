package py.org.fundacionparaguaya.pspserver.families.family.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.families.family.domain.Family;
import py.org.fundacionparaguaya.pspserver.families.family.repository.FamilyRepository;

/**
 * 
 * <p>
 * Implementation of Family services
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-14
 * @version 1.0
 */
@Service
public class FamilyServiceImpl implements FamilyService {

	protected static Logger logger = Logger.getLogger(FamilyServiceImpl.class);

	@Autowired
	FamilyRepository familyRepository;

	@Override
	public Family save(Family entity) {
		return familyRepository.save(entity);
	}

	@Override
	public Family getById(Serializable id) {
		return familyRepository.findOne((Long) id);
	}

	@Override
	public List<Family> getAll() {
		return familyRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		familyRepository.delete((Long) id);
	}

}
