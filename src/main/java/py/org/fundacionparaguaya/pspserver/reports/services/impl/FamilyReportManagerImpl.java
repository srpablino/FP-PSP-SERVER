package py.org.fundacionparaguaya.pspserver.reports.services.impl;

import static org.springframework.data.jpa.domain.Specifications.where;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byOrganization;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byApplication;
import static py.org.fundacionparaguaya.pspserver.surveys.specifications.SnapshotEconomicSpecification.forFamily;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.reports.dtos.CsvDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyOrganizationReportDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyReportFilterDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilySnapshotReportDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.ReportDTO;
import py.org.fundacionparaguaya.pspserver.reports.mapper.FamilyReportMapper;
import py.org.fundacionparaguaya.pspserver.reports.services.FamilyReportManager;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotIndicatorMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.specifications.SnapshotEconomicSpecification;
import py.org.fundacionparaguaya.pspserver.utils.ConverterString;

/**
 *
 * @author mgonzalez
 *
 */
@Service
public class FamilyReportManagerImpl implements FamilyReportManager {

    private static final List<String> DEFAULT_HEADRES = Arrays.asList("Organization Code", "Organization Name",
            "Organization Status", "Family Code", "Family Name", "Family Status", "Snapshot Created At");

    private FamilyRepository familyRepository;

    private FamilyReportMapper familyReportMapper;

    private SnapshotEconomicRepository snapshotRepository;

    private SnapshotIndicatorMapper snapshotMapper;

    private ConverterString stringConverter;

    public FamilyReportManagerImpl(FamilyRepository familyRepository, FamilyReportMapper familyReportMapper,
            SnapshotEconomicRepository snapshotRepository, SnapshotIndicatorMapper snapshotMapper,
            ConverterString stringConverter) {
        this.familyRepository = familyRepository;
        this.familyReportMapper = familyReportMapper;
        this.snapshotRepository = snapshotRepository;
        this.snapshotMapper = snapshotMapper;
        this.stringConverter = stringConverter;
    }

    @Override
    public List<FamilyOrganizationReportDTO> listFamilyByOrganizationAndCreatedDate(FamilyReportFilterDTO filters) {

        try {

            List<FamilyEntity> families = new ArrayList<>();

            Sort sort = new Sort(new Sort.Order(Direction.ASC, "organization.name"));

            if (filters.getOrganizationId() != null) {
                families = familyRepository.findAll(
                        where(byOrganization(filters.getOrganizationId())).and(FamilySpecification
                                .byCreatedAt(getDateFormat(filters.getDateFrom()), getDateFormat(filters.getDateTo()))),
                        sort);

            } else if (filters.getApplicationId() != null) {
                families = familyRepository.findAll(
                        where(byApplication(filters.getApplicationId())).and(FamilySpecification
                                .byCreatedAt(getDateFormat(filters.getDateFrom()), getDateFormat(filters.getDateTo()))),
                        sort);
            }

            Map<OrganizationEntity, List<FamilyEntity>> groupByOrganization = families.stream()
                    .collect(Collectors.groupingBy(f -> f.getOrganization()));

            List<FamilyOrganizationReportDTO> toRet = new ArrayList<>();

            groupByOrganization.forEach((k, v) -> {
                FamilyOrganizationReportDTO fa = new FamilyOrganizationReportDTO(k.getName(), k.getCode(),
                        k.getDescription(), k.isActive());
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

        List<FamilySnapshotReportDTO> toRet = new ArrayList<>();

        Sort sort = new Sort(new Sort.Order(Direction.ASC, "family.name"), new Sort.Order(Direction.ASC, "createdAt"));

        if (filters.getDateFrom() != null && filters.getDateTo() != null && filters.getFamilyId() != null) {

            List<SnapshotEconomicEntity> snapshots = snapshotRepository.findAll(
                    where(forFamily(filters.getFamilyId())).and(SnapshotEconomicSpecification
                            .byCreatedAt(getDateFormat(filters.getDateFrom()), getDateFormat(filters.getDateTo()))),
                    sort);

            Map<SurveyEntity, List<SnapshotEconomicEntity>> groupBySurvey = snapshots.stream()
                    .collect(Collectors.groupingBy(s -> s.getSurveyDefinition()));

            groupBySurvey.forEach((k, v) -> {

                FamilySnapshotReportDTO familySnapshots = new FamilySnapshotReportDTO(filters.getFamilyId(),
                        k.getTitle());
                familySnapshots.setSnapshots(getSnasphots(v));
                toRet.add(familySnapshots);

            });

        }

        return toRet;
    }

    private String getDateFormat(String date) {
        date = date.replace("/", "-");
        date = date + " 00:00:00";

        return date;
    }

    private ReportDTO getSnasphots(List<SnapshotEconomicEntity> snapshots) {

        ReportDTO report = new ReportDTO();

        report.getHeaders().add("Created At");

        List<SurveyData> rows = new ArrayList<>();
        List<List<String>> rowsValues = new ArrayList<>();

        report.getHeaders().addAll(snapshotMapper.getStaticPropertiesNames());

        for (SnapshotEconomicEntity s : snapshots) {

            s.getSnapshotIndicator().getAdditionalProperties().forEach((k, v) -> {
                if (!report.getHeaders().contains(k)) {
                    report.getHeaders().add(stringConverter.getNameFromCamelCase(k));
                }
            });
            SurveyData data = snapshotMapper.entityToDto(s.getSnapshotIndicator());
            data.put("createdAt", s.getCreatedAtLocalDateString());
            rows.add(data);
        }

        for (SurveyData data : rows) {

            List<String> row = new ArrayList<>();

            for (String header : report.getHeaders()) {

                if (data.containsKey(stringConverter.getCamelCaseFromName(header))) {
                    row.add(data.getAsString(stringConverter.getCamelCaseFromName(header)));
                } else {
                    row.add("");
                }
            }
            rowsValues.add(row);
        }

        report.setRows(rowsValues);
        return report;

    }

    private ReportDTO getOrganizationAndFamilyData(List<SnapshotEconomicEntity> snapshots) {

        ReportDTO report = new ReportDTO();

        report.getHeaders().addAll(DEFAULT_HEADRES);

        List<SurveyData> rows = new ArrayList<>();
        List<List<String>> rowsValues = new ArrayList<>();

        report.getHeaders().addAll(snapshotMapper.getStaticPropertiesNames());

        for (SnapshotEconomicEntity s : snapshots) {

            s.getSnapshotIndicator().getAdditionalProperties().forEach((k, v) -> {
                if (!report.getHeaders().contains(k)) {
                    report.getHeaders().add(stringConverter.getNameFromCamelCase(k));
                }
            });
            SurveyData data = snapshotMapper.entityToDto(s.getSnapshotIndicator());
            data.put("snapshotCreatedAt", s.getCreatedAtLocalDateString());
            data.put("organizationCode", s.getFamily().getOrganization().getCode());
            data.put("organizationName", s.getFamily().getOrganization().getName());
            data.put("organizationStatus", s.getFamily().getOrganization().isActive() ? "Active" : "Inactive");
            data.put("familyCode", s.getFamily().getCode());
            data.put("familyName", s.getFamily().getName());
            data.put("familyStatus", s.getFamily().isActive() ? "Active" : "Inactive");
            rows.add(data);
        }

        for (SurveyData data : rows) {

            List<String> row = new ArrayList<>();

            for (String header : report.getHeaders()) {

                if (data.containsKey(stringConverter.getCamelCaseFromName(header))) {
                    row.add(data.getAsString(stringConverter.getCamelCaseFromName(header)));
                } else {
                    row.add("");
                }
            }
            rowsValues.add(row);
        }

        report.setRows(rowsValues);
        return report;

    }

    @Override
    public CsvDTO generateCSVSnapshotByOrganizationAndCreatedDate(FamilyReportFilterDTO filters) {
        List<SnapshotEconomicEntity> snapshots = new ArrayList<>();

        Sort sort = new Sort(new Sort.Order(Direction.ASC, "family.organization.name"),
                new Sort.Order(Direction.ASC, "family.name"), new Sort.Order(Direction.ASC, "createdAt"));

        if (filters.getDateFrom() != null && filters.getDateTo() != null && filters.getApplicationId() != null) {

            snapshots = snapshotRepository
                    .findAll(where(SnapshotEconomicSpecification.byApplication(filters.getApplicationId()))
                            .and(SnapshotEconomicSpecification.byCreatedAt(getDateFormat(filters.getDateFrom()),
                                    getDateFormat(filters.getDateTo()))), sort);

        } else if (filters.getDateFrom() != null && filters.getDateTo() != null
                && filters.getOrganizationId() != null) {

            snapshots = snapshotRepository
                    .findAll(where(SnapshotEconomicSpecification.byOrganization(filters.getOrganizationId()))
                            .and(SnapshotEconomicSpecification.byCreatedAt(getDateFormat(filters.getDateFrom()),
                                    getDateFormat(filters.getDateTo()))), sort);
        }

        ReportDTO report = new ReportDTO();
        report = getOrganizationAndFamilyData(snapshots);

        StringWriter buffer = new StringWriter();
        report.getHeaders().stream().forEachOrdered((h) -> buffer.write(h + ","));
        buffer.append('\n');

        for (List<String> row : report.getRows()) {
            row.stream().forEachOrdered((h) -> buffer.write(h + ","));
            buffer.append('\n');
        }

        CsvDTO csv = new CsvDTO();
        csv.setCsv(buffer.toString());

        return csv;
    }
}
