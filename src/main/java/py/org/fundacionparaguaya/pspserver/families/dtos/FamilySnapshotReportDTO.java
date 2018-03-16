package py.org.fundacionparaguaya.pspserver.families.dtos;

import java.io.Serializable;
import java.util.List;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;

/**
 *
 * @author mgonzalez
 *
 */
public class FamilySnapshotReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private List<SurveyData> snapshots;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SurveyData> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<SurveyData> snapshots) {
        this.snapshots = snapshots;
    }

}
