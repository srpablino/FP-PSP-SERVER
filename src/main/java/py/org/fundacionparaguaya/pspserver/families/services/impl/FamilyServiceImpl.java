package py.org.fundacionparaguaya.pspserver.families.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.entities.PersonEntity;
import py.org.fundacionparaguaya.pspserver.families.mapper.FamilyMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.repositories.OrganizationRepository;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;
import py.org.fundacionparaguaya.pspserver.system.repositories.CityRepository;
import py.org.fundacionparaguaya.pspserver.system.repositories.CountryRepository;

@Service
public class FamilyServiceImpl implements FamilyService {

    private Logger LOG = LoggerFactory.getLogger(FamilyServiceImpl.class);

    private final FamilyMapper familyMapper;

    private final FamilyRepository familyRepository;

    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;

    private final OrganizationRepository organizationRepository;
    
    private final EntityManager em;

    private static final String SPACE = " ";

    public FamilyServiceImpl(FamilyRepository familyRepository, FamilyMapper familyMapper,
    		CountryRepository countryRepository, CityRepository cityRepository,
            OrganizationRepository organizationRepository, EntityManager em) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.organizationRepository = organizationRepository;
        this.em = em;
    }

    @Override
    public FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO) {
        checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

        return Optional.ofNullable(familyRepository.findOne(familyId)).map(family -> {
            BeanUtils.copyProperties(familyDTO, family);
            LOG.debug("Changed Information for Family: {}", family);
            return family;
        }).map(familyMapper::entityToDto).orElseThrow(() -> new UnknownResourceException("Family does not exist"));
    }

    @Override
    public FamilyDTO addFamily(FamilyDTO familyDTO) {
        FamilyEntity family = new FamilyEntity();
        BeanUtils.copyProperties(familyDTO, family);
        FamilyEntity newFamily = familyRepository.save(family);
        return familyMapper.entityToDto(newFamily);
    }

    @Override
    public FamilyDTO getFamilyById(Long familyId) {
        checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

        return Optional.ofNullable(familyRepository.findOne(familyId)).map(familyMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Family does not exist"));
    }

    @Override
    public List<FamilyDTO> getAllFamilies() {
        List<FamilyEntity> families = familyRepository.findAll();
        return familyMapper.entityListToDtoList(families);
    }

    @Override
    public void deleteFamily(Long familyId) {
        checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

        Optional.ofNullable(familyRepository.findOne(familyId)).ifPresent(family -> {
            familyRepository.delete(family);
            LOG.debug("Deleted Family: {}", family);
        });
    }

    @Override
    public String generateFamilyCode(PersonEntity person) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String birthdate = person.getBirthdate().format(formatter);

        String code = person.getCountryOfBirth().getAlfa2Code().concat(".")
                .concat(person.getFirstName().substring(0, 1)).concat(person.getLastName().substring(0, 1)).concat(".")
                .concat(birthdate);

        return code;
    }

    @Override
    public List<FamilyDTO> getFamiliesByFilter(Long organizationId, Long countryId, Long cityId, String freeText) {
		
	  	List<FamilyEntity> listFamilies = new ArrayList<FamilyEntity>();
	  	
	  	if (organizationId != null && countryId != null && cityId != null) {
	  		listFamilies = familyRepository.findByOrganizationIdAndCountryIdAndCityIdAndNameContainingIgnoreCase(organizationId, countryId, cityId, freeText);
		}else{
			listFamilies = familyRepository.findByNameContainingIgnoreCase(freeText);
		}
		
		return familyMapper.entityListToDtoList(listFamilies);
	}
    
    @Override
    public List<FamilyDTO> listFamilies(Long organizationId, Long countryId, Long cityId, String name, UserDetailsDTO userDetails) {
		
    	Long applicationId = Optional.ofNullable(userDetails.getApplication())
				.orElse(new ApplicationDTO()).getId();
		
		Long organization = Optional.ofNullable(Optional.ofNullable(userDetails.getOrganization())
				.orElse(new OrganizationDTO()).getId())
				.orElse(organizationId);
		
		List<FamilyEntity> entityList = filterFamilies(applicationId, organization, countryId, cityId, name);
		
		return familyMapper.entityListToDtoList(entityList);
	}
    
    private List<FamilyEntity> filterFamilies(Long applicationId, Long organizationId, Long countryId, Long cityId, String name) {
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<FamilyEntity> criteriaQuery = qb.createQuery(FamilyEntity.class);
		Root<FamilyEntity> root = criteriaQuery.from(FamilyEntity.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (applicationId != null) {
			Join<FamilyEntity, ApplicationEntity> joinApplication = root.join("application");
			Expression<Long> byApplicationId = joinApplication.<Long> get("id");
			predicates.add(qb.equal(byApplicationId, applicationId));
		}
		
		if (organizationId != null) {
			Join<FamilyEntity, OrganizationEntity> joinOrganization = root.join("organization");
			Expression<Long> byOrganizationId = joinOrganization.<Long> get("id");
			predicates.add(qb.equal(byOrganizationId, organizationId));
		}
		
		if (countryId != null) {
			Join<FamilyEntity, CountryEntity> joinCountry = root.join("country");
			Expression<Long> byCountryId = joinCountry.<Long> get("id");
			predicates.add(qb.equal(byCountryId, countryId));
		}
		
		if (cityId != null) {
			Join<FamilyEntity, CityEntity> joinCity = root.join("city");
			Expression<Long> byCityId = joinCity.<Long> get("id");
			predicates.add(qb.equal(byCityId, cityId));
		}
		
		if(StringUtils.isNotEmpty(name)) {
			String nameParamQuery = "%" + name.toLowerCase().replaceAll("\\s", "%") + "%";
			Expression<String> likeName = qb.lower(root.<String> get("name"));
			predicates.add(qb.like(likeName, nameParamQuery));
		}
		
		criteriaQuery.where(qb.and(predicates.toArray(new Predicate[predicates.size()])));
		criteriaQuery.orderBy(qb.asc(root.get("name")));
		
		TypedQuery<FamilyEntity> tq = em.createQuery(criteriaQuery);
		
		return tq.getResultList();
	}
    
    @Override
    public FamilyEntity createFamilyFromSnapshot(NewSnapshot snapshot, String code, PersonEntity person) {
        FamilyEntity newFamily = new FamilyEntity();
        newFamily.setPerson(person);
        newFamily.setCode(code);
        newFamily.setName(person.getFirstName().concat(SPACE).concat(person.getLastName()));
        newFamily.setLocationPositionGps(snapshot.getEconomicSurveyData().getAsString("familyUbication"));

        Optional<CountryEntity> country = countryRepository
                .findByCountry(snapshot.getEconomicSurveyData().getAsString("familyCountry"));
        newFamily.setCountry(country.orElse(null));

        Optional<CityEntity> city = cityRepository
                .findByCity(snapshot.getEconomicSurveyData().getAsString("familyCity"));
        newFamily.setCity(city.orElse(null));
        
        if(snapshot.getOrganizationId()!=null) {
            OrganizationEntity organization = organizationRepository.findOne(snapshot.getOrganizationId());
            newFamily.setOrganization(organization);
            newFamily.setApplication(organization.getApplication());
        }

        newFamily = familyRepository.save(newFamily);

        return newFamily;
    }
}
