package py.org.fundacionparaguaya.pspserver.network.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.OrganizationMapper;
import py.org.fundacionparaguaya.pspserver.network.repositories.OrganizationRepository;
import py.org.fundacionparaguaya.pspserver.network.services.OrganizationService;


@Service
public class OrganizationServiceImpl implements OrganizationService {

	 private Logger LOG = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	 private OrganizationRepository organizationRepository;
	
	 private final OrganizationMapper organizationMapper;
	 
	 public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
		this.organizationRepository = organizationRepository;
		this.organizationMapper = organizationMapper;
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

	

}
