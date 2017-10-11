package py.org.fundacionparaguaya.pspserver.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ForwardingMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigovillalba on 10/9/17.
 */
public class PropertyTitle extends ForwardingMap<String, String> {


    public PropertyTitle(Lang lang, String value) {
        propertyTitleMap.put(lang.toString(), value);
    }

    public static PropertyTitle of(String value) {
        return new PropertyTitle(Lang.DEFAULT, value);
    }

    public enum Lang {
        DEFAULT("es"), ES("es"), ENG("eng");

        private String value;

        Lang(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static Lang fromValue(String text) {
            for (Lang b : Lang.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }

            return null;
        }
    }

    private Map<String, String> propertyTitleMap = new HashMap<>();

    @Override
    protected Map<String, String> delegate() {
        return propertyTitleMap;
    }

}
