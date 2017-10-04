package py.org.fundacionparaguaya.pspserver.forms.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.opendatakit.aggregate.odktables.rest.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/18/17.
 */
public class SurveySocioEconomicAnswerDTO {

    private RowResourceList rowResourceList;
    private RowResource rowResource;
    private SurveySocioEconomicDTO survey;

    public SurveySocioEconomicAnswerDTO(){ };

    private SurveySocioEconomicAnswerDTO(RowResourceList rowResourceList, RowResource rowResource, SurveySocioEconomicDTO survey) {
        this.rowResource = rowResource;
        this.rowResourceList = rowResourceList;
        this.survey = survey;
    }

    private static SurveySocioEconomicAnswerDTO of(RowResourceList rowOutcomeList) {
        checkNotNull(rowOutcomeList);
        return new SurveySocioEconomicAnswerDTO(rowOutcomeList, null, null);
    }


    public static SurveySocioEconomicAnswerDTO of(RowResource rowResource, SurveySocioEconomicDTO survey) {
        checkNotNull(rowResource);
        return new SurveySocioEconomicAnswerDTO(null, rowResource, survey);
    }

    @JsonIgnore
    public List<DataKeyValue> getFlatDataKeyValues() {
        return  this.getDatakeyValues().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<ArrayList<DataKeyValue>> getDatakeyValues() {
         return rowResourceList.getRows().stream()
                .map(Row::getValues)
                .collect(Collectors.toList());
    }

    public RowResource getRowResource() {
        return rowResource;
    }

    public SurveySocioEconomicDTO getSurvey() {
        return survey;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("rowResourceList", rowResourceList)
                .add("rowResource", rowResource)
                .add("survey", survey)
                .toString();
    }
}
