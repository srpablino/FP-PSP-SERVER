package py.org.fundacionparaguaya.pspserver.network.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.OrganizationMapper;
import py.org.fundacionparaguaya.pspserver.network.repositories.OrganizationRepository;
import py.org.fundacionparaguaya.pspserver.network.services.OrganizationService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;


@Service
public class OrganizationServiceImpl implements OrganizationService {

	 private Logger LOG = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	 private OrganizationRepository organizationRepository;
	
	 private final OrganizationMapper organizationMapper;
	 
	 private final EntityManager em;
	 
	 public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper, EntityManager em) {
		this.organizationRepository = organizationRepository;
		this.organizationMapper = organizationMapper;
		this.em = em;
	}

	@Override
	public OrganizationDTO updateOrganization(Long organizationId, OrganizationDTO organizationDTO) {
		checkArgument(organizationId > 0, "Argument was %s but expected nonnegative", organizationId);

		return Optional.ofNullable(organizationRepository.findOne(organizationId))
                .map(organization -> {
                	organization.setName(organizationDTO.getName());
                    organization.setDescription(organizationDTO.getDescription());
					LOG.debug("Changed Information for Organization: {}", organization);
					return organizationRepository.save(organization);
			    })
                .map(organizationMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Organization does not exist"));
	}

	@Override
	public OrganizationDTO addOrganization(OrganizationDTO organizationDTO) {
		OrganizationEntity organization = new OrganizationEntity();
		BeanUtils.copyProperties(organizationDTO, organization);
		OrganizationEntity newOrganization= organizationRepository.save(organization);
		return organizationMapper.entityToDto(newOrganization);
	}

	@Override
	public OrganizationDTO getOrganizationById(Long organizationId) {
		checkArgument(organizationId > 0, "Argument was %s but expected nonnegative", organizationId);

        return Optional.ofNullable(organizationRepository.findOne(organizationId))
                .map(organizationMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Organization does not exist"));
	}

	@Override
	public List<OrganizationDTO> getAllOrganizations() {
		List<OrganizationEntity> organizations = organizationRepository.findAll();
		return organizationMapper.entityListToDtoList(organizations);
	}

	@Override
	public void deleteOrganization(Long organizationId) {
		checkArgument(organizationId > 0, "Argument was %s but expected nonnegative", organizationId);

        Optional.ofNullable(organizationRepository.findOne(organizationId))
                .ifPresent(organization -> {
                	organizationRepository.delete(organization);
                    LOG.debug("Deleted Organization: {}", organization);
                });
		
	}

	@Override
	public Page<OrganizationDTO> listOrganizations(PageRequest pageRequest, UserDetailsDTO userDetails) {		
		Page<OrganizationEntity> pageResponse = filterOrganizations(pageRequest, userDetails.getApplication(),
				userDetails.getOrganization());
		
		if (pageResponse != null) {
			return pageResponse.map(new Converter<OrganizationEntity, OrganizationDTO>() {
			    @Override
				public OrganizationDTO convert(OrganizationEntity source) {
			    	return organizationMapper.entityToDto(source);
				}
			});
		}
		
		return null;
	}
	
	private Page<OrganizationEntity> filterOrganizations(PageRequest pageRequest, ApplicationDTO application, OrganizationDTO organization) {
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<OrganizationEntity> criteriaQuery = qb.createQuery(OrganizationEntity.class);
		Root<OrganizationEntity> root = criteriaQuery.from(OrganizationEntity.class);
		Join<OrganizationEntity, ApplicationEntity> joinApplication = root.join("application");
		List<Predicate> predicates = new ArrayList<>();
		
		if (application != null && application.getId() != null) {
			Expression<Long> byIdApplication = joinApplication.<Long> get("id");
			predicates.add(qb.equal(byIdApplication, application.getId()));
		}
		
		if (organization != null && organization.getId() != null) {
			Expression<Long> byIdOrganization = root.<Long> get("id");
			predicates.add(qb.equal(byIdOrganization, organization.getId()));
		}
		
		//search only active organizations
		Expression<Boolean> isActive = root.<Boolean> get("isActive");
		predicates.add(qb.isTrue(isActive));
		
		criteriaQuery.where(qb.and(predicates.toArray(new Predicate[predicates.size()])));
		criteriaQuery.orderBy(qb.asc(root.get("name")));
		
		TypedQuery<OrganizationEntity> tq = em.createQuery(criteriaQuery);
	    int totalRows = tq.getResultList().size();
		tq.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
		tq.setMaxResults(pageRequest.getPageSize());
		
		return new PageImpl<OrganizationEntity>(tq.getResultList(), pageRequest, totalRows);
	}

}
