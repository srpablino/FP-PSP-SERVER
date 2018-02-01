package py.org.fundacionparaguaya.pspserver.surveys.dtos;
public class TopOfIndicators {
        String indicatorName;
        Integer totalGreen;
        Integer totalYellow;
        Integer totalRed;

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