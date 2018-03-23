package py.org.fundacionparaguaya.pspserver.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author mgonzalez
 *
 */
@Component
public class StringConverter {

    public StringConverter() {
        super();
    }

    public String getNameFromCamelCase(String name) {
        return StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), " "));
    }

    public String getCamelCaseFromName(String name) {

        String[] parts = name.split(" ");
        if (parts.length == 0) {
            return "";
        }
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString.substring(0, 1).toLowerCase() + camelCaseString.substring(1);

    }

    public String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}
