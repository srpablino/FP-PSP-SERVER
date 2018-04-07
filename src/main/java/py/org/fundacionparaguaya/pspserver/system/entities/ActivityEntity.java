package py.org.fundacionparaguaya.pspserver.system.entities;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.common.entities.LocalDateTimeConverter;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "activity", schema = "system")
public class ActivityEntity extends BaseEntity {

    @Id
    @GenericGenerator(name = "activityFeedSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
        @org.hibernate.annotations.Parameter(name =
                SequenceStyleGenerator.SCHEMA, value = "system"),
        @org.hibernate.annotations.Parameter(name =
                SequenceStyleGenerator.SEQUENCE_PARAM, value = "activity_activity_id_seq"),
        @org.hibernate.annotations.Parameter(name =
                SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
        @org.hibernate.annotations.Parameter(name =
                SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(generator = "activityFeedSequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "activity_key")
    private String activityKey;

    @Column(name = "activity_params")
    private String activityParams;

    @Column(name = "activity_role")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role activityRole;

    @ManyToOne(targetEntity = OrganizationEntity.class)
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @ManyToOne(targetEntity = ApplicationEntity.class)
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "created_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public ApplicationEntity getApplication() {
        return application;
    }

    public void setApplication(ApplicationEntity application) {
        this.application = application;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void preSave() {
        this.createdAt = LocalDateTime.now();
    }

    @Transient
    public String getCreatedAtAsISOString() {
        if (this.createdAt != null) {
            return createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        return null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id)
                .add("activityKey", activityKey)
                .add("activityParams", activityParams)
                .add("activityRole", activityRole)
                .add("organization", organization)
                .add("application", application)
                .add("user", user)
                .add("createdAt", createdAt)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (id == null || obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ActivityEntity toCompare = (ActivityEntity) obj;
        return id.equals(toCompare.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
