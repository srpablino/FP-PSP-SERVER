package py.org.fundacionparaguaya.pspserver.forms.dtos;

import org.opendatakit.aggregate.odktables.rest.entity.DataKeyValue;
import org.opendatakit.aggregate.odktables.rest.entity.Row;
import org.opendatakit.aggregate.odktables.rest.entity.RowResource;

import java.util.Comparator;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public class SurveyIndicatorDTO {

    private String name;

    private String optionSelected;


    public SurveyIndicatorDTO() {}

    private SurveyIndicatorDTO(String name, String optionSelected) {
        this.name = name;
        this.optionSelected = optionSelected;
    }

    public static SurveyIndicatorDTO of(String question, String answer) {
        return new SurveyIndicatorDTO(question, answer);
    }

    public String getName() {
        return name;
    }

    public String getOptionSelected() {
        return optionSelected;
    }
    
}
