package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import py.org.fundacionparaguaya.pspserver.surveys.entities.OdkRowReferenceEntity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public class OdkRowReferenceDTO {


    private String tableId;

    @JsonIgnore
    private String schemaEtag;

    @JsonIgnore
    private String rowId;

    public OdkRowReferenceDTO() {}

    private OdkRowReferenceDTO(String tableId, String schemaEtag) {
        this.tableId = tableId;
        this.schemaEtag = schemaEtag;
        this.rowId = null;
    }

    public OdkRowReferenceDTO(String odkTableId, String odkSchemEtag, String odkRowId) {
        this.tableId = odkTableId;
        this.schemaEtag =odkSchemEtag;
        this.rowId = odkRowId;
    }

    public static OdkRowReferenceDTO of(String tableId, String schemaEtag) {
        checkNotNull(tableId);
        checkNotNull(schemaEtag);
        return new OdkRowReferenceDTO(tableId, schemaEtag);
    }

    public static OdkRowReferenceDTO of(OdkRowReferenceEntity surveyIndicator) {
        checkNotNull(surveyIndicator);
        checkNotNull(surveyIndicator.getOdkTableId());
        checkNotNull(surveyIndicator.getOdkSchemEtag());
        checkNotNull(surveyIndicator.getOdkRowId());
        return new OdkRowReferenceDTO(surveyIndicator.getOdkTableId(),
                surveyIndicator.getOdkSchemEtag(),
                surveyIndicator.getOdkRowId());
    }


    public static OdkRowReferenceDTO of(String odkTableId) {
        checkNotNull(odkTableId);
        return new OdkRowReferenceDTO(odkTableId, null);
    }

    public String getTableId() {
        return tableId;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tableId", tableId)
                .add("schemaEtag", schemaEtag)
                .toString();
    }

    public String getSchemaEtag() {
        return schemaEtag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OdkRowReferenceDTO that = (OdkRowReferenceDTO) o;

        return Objects.equal(this.tableId, that.tableId) &&
                Objects.equal(this.schemaEtag, that.schemaEtag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tableId, schemaEtag);
    }


    public String getRowId() {
        return rowId;
    }

}
