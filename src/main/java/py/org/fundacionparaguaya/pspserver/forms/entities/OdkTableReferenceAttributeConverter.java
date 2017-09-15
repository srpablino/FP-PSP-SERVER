package py.org.fundacionparaguaya.pspserver.forms.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Converter(autoApply = true)
public class OdkTableReferenceAttributeConverter  implements AttributeConverter<OdkTableReference, String>{

    @Override
    public String convertToDatabaseColumn(OdkTableReference odkTableReference) {
        if (odkTableReference == null) {
            return null;
        }
        return odkTableReference.getTableId();
    }

    @Override
    public OdkTableReference convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return OdkTableReference.of(dbData);
    }
}
