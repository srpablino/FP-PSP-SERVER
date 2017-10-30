package py.org.fundacionparaguaya.pspserver.surveys.services.impl;

import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.surveys.entities.StopLightType;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.PropertyAttributeSupport;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.surveys.validation.*;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static py.org.fundacionparaguaya.pspserver.surveys.validation.MultipleSchemaValidator.all;
import static py.org.fundacionparaguaya.pspserver.surveys.validation.PropertyValidator.validType;
import static py.org.fundacionparaguaya.pspserver.surveys.validation.SchemaValidator.markedAsRequired;
import static py.org.fundacionparaguaya.pspserver.surveys.validation.SchemaValidator.presentInSchema;
import static py.org.fundacionparaguaya.pspserver.surveys.validation.SchemaValidator.requiredValue;
import static py.org.fundacionparaguaya.pspserver.surveys.validation.SurveyUISchemaValidator.presentInGroup;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    private final SnapshotEconomicRepository repository;

    private final SurveyRepository definitionRepository;

    private final PropertyAttributeSupport propertyAttributeSupport;

    public SurveyServiceImpl(SnapshotEconomicRepository repository,
                             SurveyRepository definitionRepository, PropertyAttributeSupport propertyAttributeSupport) {
        this.repository = repository;
        this.definitionRepository = definitionRepository;
        this.propertyAttributeSupport = propertyAttributeSupport;
    }

    @Override
    public SurveyDefinition addSurveyDefinition(NewSurveyDefinition surveyDefinition) {

        ValidationResults results = validateSchemas(surveyDefinition);
        if (!results.isValid()) {
            throw new CustomParameterizedException("Invalid Survey Schema", results.asMap());
        }

        SurveyEntity entity = this.definitionRepository
                .save(SurveyEntity.of(new SurveyDefinition().surveySchema(surveyDefinition.getSurveySchema())
                        .surveyUISchema(surveyDefinition.getSurveyUISchema())));

        return new SurveyDefinition().id(entity.getId()).surveySchema(entity.getSurveyDefinition().getSurveySchema())
                .surveyUISchema(entity.getSurveyDefinition().getSurveyUISchema());
    }

    private ValidationResults validateSchemas(NewSurveyDefinition surveyDefinition) {
        ValidationResults results = ValidationSupport.validResults();
        MultipleSchemaValidator schemaValidator = all(SchemaValidator.presentInSchema(), markedAsRequired());

        propertyAttributeSupport.getPropertyAttributes().stream()
                .filter(attr -> attr.getStoptLightType() == StopLightType.MANDATORY)
                .forEach(attr -> {
                    results.addAll(
                            schemaValidator.apply(surveyDefinition.getSurveySchema(), attr.getPropertySchemaName(), null)
                    );
                });

        propertyAttributeSupport.getPropertyAttributes().stream()
                .forEach(attr -> {
                    results.add(presentInGroup().apply(surveyDefinition.getSurveyUISchema(), attr));
                });

        return results;
    }

    @Override
    public SurveyDefinition getSurveyDefinition(Long surveyId) {
        checkArgument(surveyId > 0, "Argument was %s but expected nonnegative", surveyId);

        return Optional.ofNullable(definitionRepository.findOne(surveyId))
                .map(entity -> new SurveyDefinition().id(entity.getId())
                        .surveySchema(entity.getSurveyDefinition().getSurveySchema())
                        .surveyUiSchema(entity.getSurveyDefinition().getSurveyUISchema()))
                .orElseThrow(() -> new UnknownResourceException("Survey definition does not exist"));

    }

    @Override
    public ValidationResults checkSchemaCompliance(NewSnapshot snapshot) {
        SurveyDefinition surveyDefinition = this.getSurveyDefinition(snapshot.getSurveyId());
        SurveySchema schema = surveyDefinition.getSurveySchema();
        ValidationResults results = ValidationSupport.validResults();

        schema.getProperties().entrySet().stream()
                .forEach(propertyEntry -> {
                    Property property = propertyEntry.getValue();
                    Object propertyValue = snapshot.getAllSurveyData().get(propertyEntry.getKey());
                    results.add(
                            propertyValue != null?
                                    validType().apply(property, propertyEntry.getKey(), propertyValue):
                                    ValidationResult.valid()
                    );
                    results.add(requiredValue().apply(schema, propertyEntry.getKey(), propertyValue));
                });

        snapshot.getAllSurveyData().entrySet().stream()
                .forEach(surveyData -> {
                    results.add(presentInSchema().apply(schema, surveyData.getKey(), surveyData.getValue()));
                });

        return results;
    }



}
