package py.org.fundacionparaguaya.pspserver.surveys.services;

import org.opendatakit.aggregate.odktables.rest.entity.*;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyIndicatorDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.OdkRowReferenceDTO;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;
import py.org.fundacionparaguaya.pspserver.odkclient.PutRowsMethodObject;
import py.org.fundacionparaguaya.pspserver.odkclient.SurveyQuestion;
import py.org.fundacionparaguaya.pspserver.surveys.entities.OdkRowReferenceEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyIndicatorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class OdkServiceImpl implements OdkService {

    private final OdkClient odkClient;

    public OdkServiceImpl(OdkClient client) {
        this.odkClient = client;
    }


    @Override
    public RowOutcomeList addNewAnsweredQuestion(OdkRowReferenceDTO reference, List<SurveyIndicatorDTO> indicators) {

        List<DataKeyValue> values = indicators.stream()
                .map((indicator) -> new DataKeyValue(indicator.getName(), indicator.getOptionSelected()))
                .collect(Collectors.toList());

        PutRowsMethodObject methodObject = new PutRowsMethodObject(odkClient, reference.getTableId(), values).invoke();

        RowOutcomeList rowOutcomeList = odkClient.putRowList(reference.getTableId(),
                methodObject.getSchemaETag(),
                methodObject.getRowList());

        return rowOutcomeList;
    }


    public RowResourceList findIndicatorsBy(OdkRowReferenceDTO reference, List<SurveyIndicatorDTO> indicators) {
        checkNotNull(reference);
        checkNotNull(reference.getSchemaEtag());
        checkNotNull(reference.getTableId());
        checkNotNull(reference.getRowId());
        checkNotNull(indicators);

        String tableId = reference.getTableId();

        RowResourceList rowResourceList = odkClient.getRowResourceList(tableId, reference.getSchemaEtag(), "", true);

        ArrayList<RowResource> rowResources = filter(rowResourceList, indicators);

        return  new RowResourceList(rowResources, rowResourceList.getDataETag(), rowResourceList.getTableUri(),
                rowResourceList.getWebSafeRefetchCursor(),
                rowResourceList.getWebSafeBackwardCursor(),
                rowResourceList.getWebSafeResumeCursor(), rowResourceList.isHasMoreResults(),
                rowResourceList.isHasPriorResults());
    }

    @Override
    public OdkRowReferenceDTO fetchOdkTableRerefence(OdkRowReferenceDTO odkRowReferenceDTO) {
        String schemaETag = null;
        if (odkRowReferenceDTO.getSchemaEtag() == null) {
            TableResource tableResource = odkClient.getTableResource(odkRowReferenceDTO.getTableId());
            schemaETag = tableResource.getSchemaETag();
        } else {
            schemaETag = odkRowReferenceDTO.getSchemaEtag();
        }
        OdkRowReferenceDTO odkReference = OdkRowReferenceDTO.of(odkRowReferenceDTO.getTableId(), schemaETag);

        return odkReference;
    }

    @Override
    public Map<String, SurveyQuestion> getQuestionsDefinition(String tableId) {
        return odkClient.getQuestionsDefinition(tableId);
    }

    public RowResource findIndicator(OdkRowReferenceDTO reference) {
        checkNotNull(reference);
        checkNotNull(reference.getSchemaEtag());
        checkNotNull(reference.getTableId());
        checkNotNull(reference.getRowId());

        String tableId = reference.getTableId();

        RowResource singleRow = odkClient.getSingleRow(tableId, reference.getSchemaEtag(), reference.getRowId());

        return singleRow;
    }

    private ArrayList<RowResource> filter(RowResourceList rowResourceList, List<SurveyIndicatorDTO> indicators) {
        ArrayList<RowResource> filteredRowResources = new ArrayList<>();
        indicators.stream()
                .forEach(indicator -> {
                    ArrayList<RowResource> rowResources = filterRowResources(rowResourceList, indicator);
                    filteredRowResources.addAll(rowResources);
                });
        return filteredRowResources;
    }

    private ArrayList<RowResource> filterRowResources(RowResourceList rowResourceList, SurveyIndicatorDTO indicator) {
        String name = indicator.getName();
        String optionSelected = indicator.getOptionSelected();
        DataKeyValue dataKeyValue = new DataKeyValue(name, optionSelected);
        // Buscamos el indicador en cada una
        // de las filas (rows)
        return rowResourceList.getRows()
                .stream()
                .filter(row -> row.getValues().contains(dataKeyValue))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
