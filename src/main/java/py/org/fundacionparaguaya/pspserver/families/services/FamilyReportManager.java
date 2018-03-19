package py.org.fundacionparaguaya.pspserver.families.services;
import java.util.List;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyOrganizationReportDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyReportFilterDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilySnapshotReportDTO;

/**
 *
 * @author mgonzalez
 *
 */

public interface FamilyReportManager {

    List<FamilyOrganizationReportDTO> listFamilyByOrganizationAndCreatedDate(FamilyReportFilterDTO filters);
    List<FamilySnapshotReportDTO> listSnapshotByFamily(FamilyReportFilterDTO filters);

}
