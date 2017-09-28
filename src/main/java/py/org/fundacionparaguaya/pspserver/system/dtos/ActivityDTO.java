package py.org.fundacionparaguaya.pspserver.system.dtos;

public class ActivityDTO {

    private Long activityId;
    private Long userId;
    private String activityType;
    private String description;
    private Long organizationId;
    private Long applicationId;
    private String createAt;

    public Long getActivityId () { return activityId; }
    public void setActivityId (Long activityId) { this.activityId = activityId; }

    public Long getUserId() {return userId;}
    public void setUserId(Long userId) { this.userId = userId; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getDescription(){ return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getCreateAt() { return createAt; }
    public void setCreateAt(String createAt) { this.createAt = createAt; }
}
