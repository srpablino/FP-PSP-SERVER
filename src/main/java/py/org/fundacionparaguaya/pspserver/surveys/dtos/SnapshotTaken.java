package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class SnapshotTaken implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<LocalDate, Long> byMonth;

    public Map<LocalDate, Long> getByMonth() {
        return byMonth;
    }

    public void setByMonth(Map<LocalDate, Long> byMonth) {
        this.byMonth = byMonth;
    }

}
