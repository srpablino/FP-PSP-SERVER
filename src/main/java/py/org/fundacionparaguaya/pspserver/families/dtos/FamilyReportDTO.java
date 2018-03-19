package py.org.fundacionparaguaya.pspserver.families.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO.Builder;
/**
 *
 * @author mgonzalez
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{familyDTO.name.notNull}")
    private Long id;
    
    @NotNull(message = "{familyDTO.name.notNull}")
    private String name;

    @NotNull(message = "{familyDTO.code.notNull}")
    private String code;

    private boolean isActive;

    private String country;

    private String city;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static Builder builder() {
        return new Builder();
    }

}
