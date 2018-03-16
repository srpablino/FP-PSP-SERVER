package py.org.fundacionparaguaya.pspserver.families.services.impl;

import static org.springframework.data.jpa.domain.Specifications.where;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byOrganization;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byApplication;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byCreatedAt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyOrganizationReportDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyReportFilterDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilySnapshotReportDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.mapper.FamilyMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyReportManager;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.OrganizationMapper;

/**
 *
 * @author mgonzalez
 *
 */
@Service
public class FamilyReportManagerImpl implements FamilyReportManager {

    private FamilyRepository familyRepository;

    private FamilyMapper familyMapper;

    private OrganizationMapper organizationMapper;

    public FamilyReportManagerImpl(FamilyRepository familyRepository, FamilyMapper familyMapper,
            OrganizationMapper organizationMapper) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public FamilyOrganizationReportDTO listFamilyByOrganizationAndCreatedDate(FamilyReportFilterDTO filters) {

        try {
            System.out.println("entre a pedir la lista");
            System.out.println(filters.toString());

            List<FamilyEntity> families = new ArrayList<>();

            if (filters.getOrganizationId() != null) {
                families = familyRepository.findAll(where(byOrganization(filters.getOrganizationId()))
                        .and(byCreatedAt(filters.getDateFrom(), filters.getDateTo())));

                System.out.println(familyRepository.findAll(where(byOrganization(filters.getOrganizationId()))).get(0));

                System.out.println(familyMapper.entityToDto(
                        familyRepository.findAll(where(byOrganization(filters.getOrganizationId()))).get(0)));

            } else if (filters.getApplicationId() != null) {
                families = familyRepository.findAll(where(byApplication(filters.getApplicationId())));
            }

            Map<OrganizationEntity, List<FamilyEntity>> groupByOrganization = families.stream()
                    .collect(Collectors.groupingBy(f -> f.getOrganization()));

            Map<OrganizationDTO, List<FamilyDTO>> result = new HashMap<>();
            groupByOrganization.forEach((k, v) -> {
                result.put(organizationMapper.entityToDto(k), familyMapper.entityListToDtoList(v));
               
            });

            return new FamilyOrganizationReportDTO(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public List<FamilySnapshotReportDTO> listSnapshotByFamily(FamilyReportFilterDTO filters) {
        // TODO Auto-generated method stub
        return null;
    }

}
