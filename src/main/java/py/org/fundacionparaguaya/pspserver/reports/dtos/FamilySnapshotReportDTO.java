package py.org.fundacionparaguaya.pspserver.reports.dtos;

import java.io.Serializable;
import java.util.List;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;

/**
 *
 * @author mgonzalez
 *
 */
public class FamilySnapshotReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private List<SnapshotIndicators> snapshots;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SnapshotIndicators> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<SnapshotIndicators>snapshots) {
        this.snapshots = snapshots;
    }

}
