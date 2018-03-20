package py.org.fundacionparaguaya.pspserver.reports.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyReportDTO;

/**
 *
 * @author mgonzalez
 *
 */
@Component
public class FamilyReportMapper {
    
    public FamilyReportDTO entityToDto(FamilyEntity family) {
        FamilyReportDTO toRet = new FamilyReportDTO();
        if(family==null) {
            return toRet;
        }
        
        toRet.setId(family.getFamilyId());
        toRet.setActive(family.isActive());
        toRet.setCity(family.getCity()!=null? family.getCity().getCity() : "");
        toRet.setCode(family.getCode());
        toRet.setName(family.getName());
        toRet.setCountry(family.getCountry()!=null? family.getCountry().getCountry(): "");
        
        return toRet;
    }
    
    public List<FamilyReportDTO> entityListToDtoList(List<FamilyEntity> entityList) {
        return entityList.stream()
                   .filter(Objects::nonNull)
                   .map(this::entityToDto)
                   .collect(Collectors.toList());
   }
}
