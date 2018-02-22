package py.org.fundacionparaguaya.pspserver.network.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;

@StaticMetamodel(SurveyOrganizationEntity.class)
public class SurveyOrganizationEntity_ {

    private static volatile SingularAttribute<SurveyOrganizationEntity, Long> id;

    private static volatile SingularAttribute<SurveyOrganizationEntity, OrganizationEntity> organization;

    private static volatile SingularAttribute<SurveyOrganizationEntity, SurveyEntity> survey;

    private SurveyOrganizationEntity_() {
    }

    public static SingularAttribute<SurveyOrganizationEntity, Long> getId() {
        return id;
    }

    public static SingularAttribute<SurveyOrganizationEntity, OrganizationEntity> getOrganization() {
        return organization;
    }

    public static SingularAttribute<SurveyOrganizationEntity, SurveyEntity> getSurvey() {
        return survey;
    }

}