package py.org.fundacionparaguaya.pspserver.odkclient;

import org.opendatakit.aggregate.odktables.rest.entity.DataKeyValue;
import org.opendatakit.aggregate.odktables.rest.entity.RowList;
import org.opendatakit.aggregate.odktables.rest.entity.TableResource;

import java.util.List;
import java.util.Map;

/**
 * Created by rodrigovillalba on 9/4/17.
 */
public class PutRowsMethodObject {

    private final String tableId;
    private final List<DataKeyValue> values;
    private OdkClient odkClient;
    private String schemaETag;
    private RowList rowList;

    public PutRowsMethodObject(OdkClient odkClient, String tableId, List<DataKeyValue> values) {
        this.odkClient = odkClient;
        this.tableId = tableId;
        this.values = values;
    }

    public String getSchemaETag() {
        return schemaETag;
    }

    public RowList getRowList() {
        return rowList;
    }

    public PutRowsMethodObject invoke() {
        TableResource tableResource = odkClient.getTableResource(tableId);

        schemaETag = tableResource.getSchemaETag();
        String dataETag = tableResource.getDataETag();

        Map<String, SurveyQuestion> questions = odkClient.getQuestionsDefinition(tableId);


        rowList = QuestionsUtils.getRowsFromQuestions(dataETag, questions, values);
        return this;
    }
}