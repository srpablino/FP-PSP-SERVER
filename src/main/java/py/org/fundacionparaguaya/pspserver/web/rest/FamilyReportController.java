package py.org.fundacionparaguaya.pspserver.web.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.reports.dtos.CsvDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyOrganizationReportDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyReportFilterDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilySnapshotReportDTO;
import py.org.fundacionparaguaya.pspserver.reports.services.FamilyReportManager;

/**
 *
 * @author mgonzalez
 *
 */
@RestController
@RequestMapping(value = "/api/v1/families/reports")
public class FamilyReportController {

    private FamilyReportManager familyReportService;

    public FamilyReportController(FamilyReportManager familyReportService) {
        this.familyReportService = familyReportService;
    }

    @GetMapping(path ="/byOrganization", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<FamilyOrganizationReportDTO>> getFamiliesByOrganization(
            @RequestParam(value = "application_id", required = false) Long applicationId,
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "family_id", required = false) Long familyId,
            @RequestParam(value = "date_from", required = true) String dateFrom,
            @RequestParam(value = "date_to", required = true) String dateTo) {

        FamilyReportFilterDTO filters = new FamilyReportFilterDTO(applicationId, organizationId, familyId, dateFrom,
                dateTo);

       List<FamilyOrganizationReportDTO> families = familyReportService.listFamilyByOrganizationAndCreatedDate(filters);
       return ResponseEntity.ok(families);
    }
    
    @GetMapping(path="/indicators/family", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<FamilySnapshotReportDTO>> getSnapshotsIndicatorsByFamily(
            @RequestParam(value = "application_id", required = false) Long applicationId,
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "family_id", required = true) Long familyId,
            @RequestParam(value = "date_from", required = true) String dateFrom,
            @RequestParam(value = "date_to", required = true) String dateTo) {
       
        FamilyReportFilterDTO filters = new FamilyReportFilterDTO(applicationId, organizationId, familyId, dateFrom,
                dateTo);
        List<FamilySnapshotReportDTO> snapshots = familyReportService.listSnapshotByFamily(filters);
        
        return ResponseEntity.ok(snapshots);
    }
    
    @GetMapping(path="/indicators/csv", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CsvDTO> generateCSVSnapshotByOrganizationAndCreatedDate(
            @RequestParam(value = "application_id", required = false) Long applicationId,
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "family_id", required = true) Long familyId,
            @RequestParam(value = "date_from", required = true) String dateFrom,
            @RequestParam(value = "date_to", required = true) String dateTo) {
       
        FamilyReportFilterDTO filters = new FamilyReportFilterDTO(applicationId, organizationId, familyId, dateFrom,
                dateTo);
        CsvDTO csv = familyReportService.generateCSVSnapshotByOrganizationAndCreatedDate(filters);
        return ResponseEntity.ok(csv);
    }
}
