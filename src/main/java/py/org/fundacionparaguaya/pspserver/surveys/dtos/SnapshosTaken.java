package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import java.util.Map;

public class SnapshosTaken {

	long today;
    Map<String, Long> byMonth;
    
	public SnapshosTaken() {
		super();
	}
	
	public long getToday() {
		return today;
	}
	public void setToday(long today) {
		this.today = today;
	}
	public Map<String, Long> getByMonth() {
		return byMonth;
	}
	public void setByMonth(Map<String, Long> byMonth) {
		this.byMonth = byMonth;
	}
    
    
    
}
