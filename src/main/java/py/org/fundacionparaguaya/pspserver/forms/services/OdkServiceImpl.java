package py.org.fundacionparaguaya.pspserver.forms.services;

import org.opendatakit.aggregate.odktables.rest.entity.DataKeyValue;
import org.opendatakit.aggregate.odktables.rest.entity.RowOutcomeList;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveyIndicatorDTO;
import py.org.fundacionparaguaya.pspserver.forms.entities.OdkTableReference;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;
import py.org.fundacionparaguaya.pspserver.odkclient.PutRowsMethodObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class OdkServiceImpl implements OdkService {

    private final OdkClient client;

    public OdkServiceImpl(OdkClient client) {
        this.client = client;
    }


    @Override
    public RowOutcomeList addNewAnsweredQuestion(OdkTableReference reference, List<SurveyIndicatorDTO> indicators) {

        List<DataKeyValue> values = indicators.stream()
                .map((indicator) -> new DataKeyValue(indicator.getName(), indicator.getOptionSelected()))
                .collect(Collectors.toList());

        PutRowsMethodObject methodObject = new PutRowsMethodObject(client, reference.getTableId(), values).invoke();

        RowOutcomeList rowOutcomeList = client.putRowList(reference.getTableId(),
                methodObject.getSchemaETag(),
                methodObject.getRowList());

        return rowOutcomeList;
    }
}
