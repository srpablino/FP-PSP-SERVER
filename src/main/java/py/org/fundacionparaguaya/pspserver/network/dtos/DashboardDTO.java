/**
 * 
 */
package py.org.fundacionparaguaya.pspserver.network.dtos;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.TopOfIndicators;
import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityDTO;

/**
 * @author bsandoval
 *
 */
public class DashboardDTO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long numberOfFamilies;
    private List<ActivityDTO> activityFeed;
    private List<TopOfIndicators> topOfIndicators;

    public DashboardDTO() {
        super();
    }

    public DashboardDTO(Long numberOfFamilies) {
        super();
        this.numberOfFamilies = numberOfFamilies;
    }

    public static DashboardDTO of(Long numberOfFamilies) {
        return new DashboardDTO(numberOfFamilies);
    }

    public Long getNumberOfFamilies() {
        return numberOfFamilies;
    }

    public void setNumberOfFamilies(Long numberOfFamilies) {
        this.numberOfFamilies = numberOfFamilies;
    }

    public List<ActivityDTO> getActivityFeed() {
        return activityFeed;
    }

    public void setActivityFeed(List<ActivityDTO> activityFeed) {
        this.activityFeed = activityFeed;
    }

    public List<TopOfIndicators> getTopOfIndicators() {
        return topOfIndicators;
    }

    public void setTopOfIndicators(List<TopOfIndicators> topOfIndicators) {
        this.topOfIndicators = topOfIndicators;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("numberOfFamilies", numberOfFamilies);
        builder.append("activityFeed", activityFeed);
        return builder.build();
    }
}
