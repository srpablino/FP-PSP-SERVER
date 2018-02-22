package py.org.fundacionparaguaya.pspserver.network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import py.org.fundacionparaguaya.pspserver.network.entities.SurveyOrganizationEntity;

public interface SurveyOrganizationRepository
        extends JpaRepository<SurveyOrganizationEntity, Long>,
        JpaSpecificationExecutor<SurveyOrganizationEntity> {

    SurveyOrganizationEntity findBySurveyIdAndOrganizationId(Long surveyId,
            Long organizationId);

}
