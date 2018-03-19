package py.org.fundacionparaguaya.pspserver.families.services.impl;

import static org.springframework.data.jpa.domain.Specifications.where;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byOrganization;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byApplication;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byCreatedAt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyOrganizationReportDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyReportFilterDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilySnapshotReportDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.mapper.FamilyReportMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyReportManager;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;

/**
 *
 * @author mgonzalez
 *
 */
@Service
public class FamilyReportManagerImpl implements FamilyReportManager {
    
    private FamilyRepository familyRepository;

    private FamilyReportMapper familyReportMapper;


    public FamilyReportManagerImpl(FamilyRepository familyRepository, FamilyReportMapper familyReportMapper) {
        this.familyRepository = familyRepository;
        this.familyReportMapper = familyReportMapper;
    }

    @Override
    public List<FamilyOrganizationReportDTO> listFamilyByOrganizationAndCreatedDate(FamilyReportFilterDTO filters) {

        try {
            System.out.println("entre a pedir la lista");
            System.out.println(filters.toString());

            List<FamilyEntity> families = new ArrayList<>();

            if (filters.getOrganizationId() != null) {
                families = familyRepository.findAll(where(byOrganization(filters.getOrganizationId()))
                        .and(byCreatedAt(getDateFormat(filters.getDateFrom()), getDateFormat(filters.getDateTo()))));

            } else if (filters.getApplicationId() != null) {
                families = familyRepository.findAll(where(byApplication(filters.getApplicationId()))
                        .and(byCreatedAt(getDateFormat(filters.getDateFrom()), getDateFormat(filters.getDateTo()))));
            }

            Map<OrganizationEntity, List<FamilyEntity>> groupByOrganization = families.stream()
                    .collect(Collectors.groupingBy(f -> f.getOrganization()));

            List<FamilyOrganizationReportDTO> toRet = new ArrayList<>();
            
            groupByOrganization.forEach((k, v) -> {
               FamilyOrganizationReportDTO fa = new FamilyOrganizationReportDTO(k.getName(), k.getCode(), k.getDescription(), k.isActive());
               fa.setFamilies(familyReportMapper.entityListToDtoList(v));
               
               toRet.add(fa);

            });

            return toRet;
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
    
    private String getDateFormat(String date) {
        date = date.replace("/", "-");
        date = date+" 00:00:00";
        
        return date;
    }
    

}
