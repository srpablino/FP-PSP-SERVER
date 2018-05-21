package py.org.fundacionparaguaya.pspserver.util;

import org.springframework.beans.BeanUtils;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.DashboardDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotTaken;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;

import java.util.ArrayList;

/**
 * Created by bsandoval on 10/04/18.
 */
public class MockupUtils {

    private static final Long SURVEY_ID = 2L;
    private static final Long SNAPSHOT_ID = 2L;
    private static final Long USER_ID = 3L;
    private static final Long TERM_COND_ID = 4L;
    private static final Long PRIV_POL_ID = 5L;
    private static final String SNAPSHOT_JSON_FILE = "/snapshot.json";

    public static CountryDTO getCountryTest() {
        py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO dto = new CountryDTO();
        dto.setId(new Long(1));
        dto.setCountry("foo.COUNTRY");
        return dto;
    }

    public static CityDTO getCityTest() {
        CityDTO dto = new CityDTO();
        dto.setId(new Long(1));
        dto.setCity("foo.CITY");
        return dto;
    }

    public static OrganizationDTO getOrganizationTest() {
        return OrganizationDTO.builder()
                .id(1L)
                .name("foo.name")
                .code("foo.code")
                .description("Farmacenter")
                .isActive(true)
                .country(getCountryTest())
                .application(getApplicationTest())
                .information("foo.information")
                .dashboard(getDashboardTest())
                .build();
    }

    public static ApplicationDTO getApplicationTest() {
        return ApplicationDTO.builder()
                .id(1L)
                .name("foo.name")
                .code("foo.code")
                .description("Fundacion Paraguaya")
                .isActive(true)
                .country(getCountryTest())
                .city(getCityTest())
                .information("foo.information")
                .dashboard(getDashboardTest())
                .build();
    }

    public static DashboardDTO getDashboardTest() {
        DashboardDTO dto = new DashboardDTO();
        dto.setNumberOfFamilies(new Long(1));
        dto.setActivityFeed(new ArrayList<>());
        dto.setSnapshotIndicators(new SnapshotIndicators());
        dto.setSnapshotTaken(new SnapshotTaken());
        dto.setTopOfIndicators(new ArrayList<>());
        return dto;
    }

    public static NewSnapshot getNewSnapshot() {
        NewSnapshot newSnapshot = new NewSnapshot();
        BeanUtils.copyProperties(getSnapshot(), newSnapshot);
        return newSnapshot;
    }

    public static Snapshot getSnapshot() {
        Snapshot snapshot = (Snapshot) TestHelper.mapToObjectFromFile(SNAPSHOT_JSON_FILE, Snapshot.class);
        return new Snapshot()
                .surveyId(SURVEY_ID)
                .snapshotEconomicId(SNAPSHOT_ID)
                .userId(USER_ID)
                .termCondId(TERM_COND_ID)
                .privPolId(PRIV_POL_ID)
                .personalSurveyData(snapshot.getPersonalSurveyData())
                .economicSurveyData(snapshot.getEconomicSurveyData())
                .indicatorSurveyData(snapshot.getIndicatorSurveyData());
    }
}
