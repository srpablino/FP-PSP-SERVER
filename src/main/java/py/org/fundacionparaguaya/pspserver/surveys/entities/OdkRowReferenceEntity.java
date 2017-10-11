package py.org.fundacionparaguaya.pspserver.surveys.entities;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.OdkRowReferenceDTO;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Entity
@Table(schema = "data_collect", name = "odk_row_references")
public class OdkRowReferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odk_row_reference_id")
    private Long odkRowReferenceId;


    @Column(name = "odk_table_id")
    private String odkTableId;

    @Column(name = "odk_schema_etag")
    private String odkSchemEtag;

    @Column(name = "odk_row_id")
    private String odkRowId;


    public OdkRowReferenceEntity() {}

    public OdkRowReferenceEntity(String tableId, String schemaEtag, String rowId) {
        this.odkSchemEtag=schemaEtag;
        this.odkRowId = rowId;
        this.odkTableId = tableId;
    }

    public Long getOdkRowReferenceId() {
        return odkRowReferenceId;
    }

    public void setOdkRowReferenceId(Long odkRowReferenceId) {
        this.odkRowReferenceId = odkRowReferenceId;
    }

    public String getOdkTableId() {
        return odkTableId;
    }

    public void setOdkTableId(String odkTableId) {
        this.odkTableId = odkTableId;
    }

    public String getOdkSchemEtag() {
        return odkSchemEtag;
    }

    public void setOdkSchemEtag(String odkSchemEtag) {
        this.odkSchemEtag = odkSchemEtag;
    }

    public String getOdkRowId() {
        return odkRowId;
    }

    public void setOdkRowId(String odkRowId) {
        this.odkRowId = odkRowId;
    }

    public static OdkRowReferenceEntity of(OdkRowReferenceDTO odkRowReferenceDTO, String rowId) {
        checkNotNull(odkRowReferenceDTO);
        checkNotNull(odkRowReferenceDTO.getTableId());
        checkNotNull(odkRowReferenceDTO.getSchemaEtag());
        checkNotNull(rowId);
        return new OdkRowReferenceEntity(odkRowReferenceDTO.getTableId(), odkRowReferenceDTO.getSchemaEtag(), rowId);
    }
}
