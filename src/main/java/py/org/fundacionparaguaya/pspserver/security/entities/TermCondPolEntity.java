package py.org.fundacionparaguaya.pspserver.security.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.google.common.base.MoreObjects;

import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolType;

/**
 *
 * @author mgonzalez
 *
 */
@Entity
@Table(name = "termcondpol", schema = "security")
public class TermCondPolEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "termcondpolSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = SequenceStyleGenerator.SCHEMA,
                    value = "security"),
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM,
                value = "termcondpol_id_seq"),
            @Parameter(name = SequenceStyleGenerator.INITIAL_PARAM,
                value = "1"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM,
            value = "1")
        })
    @GeneratedValue(generator = "termcondpolSequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "html")
    private String html;

    @Column(name = "version")
    private String version;

    @Column(name = "year")
    private Integer year;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "type_cod")
    @Enumerated(EnumType.STRING)
    private TermCondPolType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public TermCondPolType getType() {
        return type;
    }

    public void setType(TermCondPolType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (id == null || obj == null
                || getClass() != obj.getClass()) {
            return false;
        }
        TermCondPolEntity toCompare = (TermCondPolEntity) obj;
        return id.equals(toCompare.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("version", version)
            .add("year", year)
            .add("created date", createdDate)
            .add("type", type)
            .toString();
    }

}
