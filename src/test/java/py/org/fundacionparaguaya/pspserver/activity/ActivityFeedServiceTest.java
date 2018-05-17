package py.org.fundacionparaguaya.pspserver.activity;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.PspServerApplication;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationSupport;
import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityFeedDTO;
import py.org.fundacionparaguaya.pspserver.system.services.ActivityService;
import py.org.fundacionparaguaya.pspserver.util.MockupUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    private static final Logger LOG = LoggerFactory.getLogger(ActivityFeedServiceTest.class);

    @Autowired
    private SnapshotService service;

    @Autowired
    private ActivityService activityService;

    @MockBean
    private SurveyService surveyService;

    private UserDetailsDTO userDetails;

    @Before
    public void init() {
        this.userDetails = getUserDetailsByRole(Role.ROLE_ROOT);
    }

    @Test
    @Rollback(true)
    public void createSnapshotActivities(){
        when(surveyService.checkSchemaCompliance(anyObject())).thenReturn(ValidationSupport.validResults());

        //first snapshot
        service.addSurveySnapshot(userDetails, MockupUtils.getNewSnapshot());

        LOG.info("Activity feed for first snapshot");
        List<ActivityFeedDTO> activityFeed = activityService.getActivitiesByUserDetails(this.userDetails);
        assertThat(activityFeed).isNotEmpty();
        LOG.info(new Gson().toJson(activityFeed));

        /*List<ActivityFeedDTO> activityFeed = new ArrayList<>();
        for (ActivityDTO activity : activities) {

            ActivityFeedDTO feedDTO = new ActivityFeedDTO();
            BeanUtils.copyProperties(activity, feedDTO);

            feedDTO.setDate(new Date());
            feedDTO.setMessage(i18n.translate(activity.getActivityKey(), activity.getActivityParams().split(",")));
            activityFeed.add(feedDTO);
        }*/

        //another snapshot
        service.addSurveySnapshot(userDetails, MockupUtils.getNewSnapshot());

        LOG.info("Activity feed for {}", Role.ROLE_ROOT);
        List<ActivityFeedDTO> activityFeedAdmin = activityService.getActivitiesByUserDetails(getUserDetailsByRole(Role.ROLE_ROOT));
        assertThat(activityFeedAdmin).isNotEmpty();
        LOG.info(new Gson().toJson(activityFeedAdmin));

        LOG.info("Activity feed for {}", Role.ROLE_HUB_ADMIN);
        List<ActivityFeedDTO> activityFeedHub = activityService.getActivitiesByUserDetails(getUserDetailsByRole(Role.ROLE_HUB_ADMIN));
        assertThat(activityFeedHub).isNotEmpty();
        LOG.info(new Gson().toJson(activityFeedHub));

        LOG.info("Activity feed for {}", Role.ROLE_APP_ADMIN);
        List<ActivityFeedDTO> activityFeedApp = activityService.getActivitiesByUserDetails(getUserDetailsByRole(Role.ROLE_APP_ADMIN));
        assertThat(activityFeedApp).isNotEmpty();
        LOG.info(new Gson().toJson(activityFeedApp));

    }

    private UserDetailsDTO getUserDetailsByRole(Role role){
        return UserDetailsDTO.builder()
                .application(MockupUtils.getApplicationTest())
                .organization(MockupUtils.getOrganizationTest())
                .grantedAuthorities(getGrantedAuthorities(role))
                .username("admin")
                .password("password")
                .build();
    }

    private List<GrantedAuthority> getGrantedAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

}
