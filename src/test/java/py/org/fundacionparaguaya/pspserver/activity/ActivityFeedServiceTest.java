package py.org.fundacionparaguaya.pspserver.activity;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.PspServerApplication;
import py.org.fundacionparaguaya.pspserver.config.I18n;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationSupport;
import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityFeedDTO;
import py.org.fundacionparaguaya.pspserver.system.services.ActivityService;
import py.org.fundacionparaguaya.pspserver.util.MockupUtils;

import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * Created by bsandoval on 09/04/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { PspServerApplication.class })
@ActiveProfiles("test")
@Transactional
public class ActivityFeedServiceTest {

    @Autowired
    private SnapshotService service;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private I18n i18n;

    @MockBean
    private SurveyService surveyService;

    private UserDetailsDTO userDetails;

    @Before
    public void init() {
        this.userDetails = UserDetailsDTO.builder()
                .application(MockupUtils.getApplicationTest())
                .organization(MockupUtils.getOrganizationTest())
                .username("admin")
                .password("password")
                .build();
    }

    @Test
    @Rollback(true)
    public void createSnapshotActivities(){
        when(surveyService.checkSchemaCompliance(anyObject())).thenReturn(ValidationSupport.validResults());

        Snapshot snapshot = service.addSurveySnapshot(userDetails, MockupUtils.getNewSnapshot());
        List<ActivityDTO> activities = activityService.getAllActivities();

        Assert.assertFalse(CollectionUtils.isEmpty(activities));
        for (ActivityDTO activity : activities) {

            ActivityFeedDTO feedDTO = new ActivityFeedDTO();
            BeanUtils.copyProperties(activity, feedDTO);

            feedDTO.setDate(new Date());
            feedDTO.setMessage(i18n.translate(activity.getActivityKey(), activity.getActivityParams().split(",")));
            System.out.println(feedDTO.toString());
        }
    }

}
