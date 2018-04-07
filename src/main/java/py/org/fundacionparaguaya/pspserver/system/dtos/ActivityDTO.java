package py.org.fundacionparaguaya.pspserver.system.dtos;

import com.google.common.base.MoreObjects;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long activityId;
    private Long userId;
    private Long applicationId;
    private Long organizationId;
    private Long familyId;
    private String activityKey;
    private String activityParams;
    private Role activityRole;
    private String createAt;

    public ActivityDTO() {
    }

    public ActivityDTO(Long activityId, Long userId, Long applicationId, Long organizationId, Long familyId,
            String activityKey, String activityParams, Role activityRole, String createAt) {
        this.activityId = activityId;
        this.userId = userId;
        this.applicationId = applicationId;
        this.organizationId = organizationId;
        this.familyId = familyId;
        this.activityKey = activityKey;
        this.activityParams = activityParams;
        this.activityRole = activityRole;
        this.createAt = createAt;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getActivityKey() {
        return activityKey;
    }

    public void setActivityKey(String activityKey) {
        this.activityKey = activityKey;
    }

    public String getActivityParams() {
        return activityParams;
    }

    public void setActivityParams(String activityParams) {
        this.activityParams = activityParams;
    }

    public Role getActivityRole() {
        return activityRole;
    }

    public void setActivityRole(Role activityRole) {
        this.activityRole = activityRole;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public static class Builder {
        private Long activityId;
        private Long userId;
        private Long applicationId;
        private Long organizationId;
        private Long familyId;
        private String activityKey;
        private List<String> activityParams;
        private Role activityRole;
        private String createAt;

        public Builder() {
            this.activityParams = new ArrayList<>();
        }

        public Builder activityId(Long activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder applicationId(Long applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder familyId(Long familyId) {
            this.familyId = familyId;
            return this;
        }

        public Builder activityKey(String activityKey) {
            this.activityKey = activityKey;
            return this;
        }

        public Builder activityParams(List<String> activityParams) {
            this.activityParams = activityParams;
            return this;
        }

        public Builder activityRole(Role activityRole) {
            this.activityRole = activityRole;
            return this;
        }

        public Builder createAt(String createAt) {
            this.createAt = createAt;
            return this;
        }

        public Builder addActivityParam(String param) {
            this.activityParams = activityParams;
            return this;
        }

        public ActivityDTO build() {
            return new ActivityDTO(activityId, userId, applicationId, organizationId, familyId,
                    activityKey, activityParams.stream().collect(Collectors.joining(",")),
                    activityRole, createAt);
        }
    }

    public static ActivityDTO.Builder builder() {
        return new ActivityDTO.Builder();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("activityId", activityId)
                .add("applicationId", applicationId)
                .add("organizationId", organizationId)
                .add("familyId", familyId)
                .add("activityKey", activityKey)
                .add("activityParams", activityParams)
                .add("createAt", createAt)
                .toString();
    }
}
