package py.org.fundacionparaguaya.pspserver.reports.dtos;

import java.io.Serializable;

/**
 *
 * @author mgonzalez
 *
 */
public class CsvDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String csv;

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

}
