package py.org.fundacionparaguaya.pspserver.forms.entities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public class OdkTableReference {

    private String tableId;

    private OdkTableReference(String tableId) {
        this.tableId = tableId;
    }

    public static OdkTableReference of(String tableId) {
        checkNotNull(tableId);
        return new OdkTableReference(tableId);
    }


    public String getTableId() {
        return tableId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OdkTableReference that = (OdkTableReference) o;

        return Objects.equal(this.tableId, that.tableId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tableId);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tableId", tableId)
                .toString();
    }
}
