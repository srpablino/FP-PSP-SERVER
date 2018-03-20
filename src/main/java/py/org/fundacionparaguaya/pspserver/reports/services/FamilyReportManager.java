package py.org.fundacionparaguaya.pspserver.reports.services;
import java.util.List;

import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyOrganizationReportDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyReportFilterDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilySnapshotReportDTO;

/**
 *
 * @author mgonzalez
 *
 */

public interface FamilyReportManager {

    List<FamilyOrganizationReportDTO> listFamilyByOrganizationAndCreatedDate(FamilyReportFilterDTO filters);
    FamilySnapshotReportDTO listSnapshotByFamily(FamilyReportFilterDTO filters);

}
