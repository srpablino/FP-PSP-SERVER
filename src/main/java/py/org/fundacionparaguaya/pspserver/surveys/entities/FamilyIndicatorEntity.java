package py.org.fundacionparaguaya.pspserver.surveys.entities;

import com.google.common.collect.Lists;
import org.hibernate.annotations.*;
import py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType;
import py.org.fundacionparaguaya.pspserver.web.models.SurveyData;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by rodrigovillalba on 10/17/17.
 */
@Entity
@Table(schema = "data_collect", name = "family_indicators")
public class FamilyIndicatorEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "family_id")
    private Long familyId;

    private String income;

    private String documentation;

    @Column(name = "drinking_water_access")
    private String dringkingWaterAccess;

    @Column(name= "nearby_health_post")
    private String nearbyHealthPost;

    private String alimentation;

    @Column(name = "garbage_disposal")
    private String garbageDisposal;

    @Column(name ="safe_house")
    private String safeHouse;

    @Column(name = "safe_bathroom")
    private String safeBathroom;

    @Column(name = "electricity_access")
    private String electricityAccess;

    private String refrigerator;

    @Column(name = "separate_bed")
    private String separateBed;

    @Column(name = "separate_bedrooms")
    private String separateBedrooms;

    @Column(name = "proper_kitchen")
    private String properKitchen;

    @Column(name = "phone")
    private String phone;

    @Column(name = "security")
    private String security;

    @Column(name = "read_and_write")
    private String readAndWrite;

    @Column(name ="middle_education")
    private String middleEducation;

    @Column(name = "social_capital")
    private String socialCapital;

    @Column(name = "information_access")
    private String informationAccess;

    @Column(name = "influence_in_public_sector")
    private String influenceInPublicSector;

    @Column(name = "awareness_of_needs")
    private String awarenessOfNeeds;

    @Column(name = "self_esteem")
    private String selfEsteem;

    @Column(name = "autonomy_decisions")
    private String autonomyDecisions;

    @Column(name = "additional_properties")
    @Type(type = "py.org.fundacionparaguaya.pspserver.surveys.entities.types.SecondJSONBUserType", parameters = {
            @org.hibernate.annotations.Parameter(name = SecondJSONBUserType.CLASS, value = "py.org.fundacionparaguaya.pspserver.web.models.SurveyData")})
    private SurveyData additionalProperties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SurveyData getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(SurveyData additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public FamilyIndicatorEntity additionalProperties(SurveyData indicatorssSurveyData) {
        this.additionalProperties = indicatorssSurveyData;
        return this;
    }
}
