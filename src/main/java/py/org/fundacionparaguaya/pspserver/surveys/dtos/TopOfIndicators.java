package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import java.io.Serializable;

public class TopOfIndicators implements Serializable {
    private static final long serialVersionUID = 1L;
    private String indicatorName;
    private Integer totalGreen;
    private Integer totalYellow;
    private Integer totalRed;

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public Integer getTotalGreen() {
        return totalGreen;
    }

    public void setTotalGreen(Integer totalGreen) {
        this.totalGreen = totalGreen;
    }

    public Integer getTotalYellow() {
        return totalYellow;
    }

    public void setTotalYellow(Integer totalYellow) {
        this.totalYellow = totalYellow;
    }

    public Integer getTotalRed() {
        return totalRed;
    }

    public void setTotalRed(Integer totalRed) {
        this.totalRed = totalRed;
    }

}