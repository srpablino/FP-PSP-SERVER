package py.org.fundacionparaguaya.pspserver.system.dtos;

import com.google.common.base.MoreObjects;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDTO;

import java.util.Date;

/**
 * Created by bsandoval on 10/04/18.
 */
public class ActivityFeedDTO extends ActivityDTO {
    private String message;
    private Date date;

    public ActivityFeedDTO() {
    }

    public ActivityFeedDTO(Long activityId, UserDTO user, ApplicationDTO application, OrganizationDTO organization,
            FamilyDTO family, String activityKey, String activityParams, Role activityRole, String createAt,
            String message, Date date) {
        super(activityId, user, application, organization, family, activityKey, activityParams, activityRole, createAt);
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ActivityFeedDTO))
            return false;

        ActivityFeedDTO that = (ActivityFeedDTO) o;

        if (message != null ? !message.equals(that.message) : that.message != null)
            return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("message", message)
                .add("date", date)
                .add("super", super.toString())
                .toString();
    }
}
