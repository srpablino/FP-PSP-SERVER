package py.org.fundacionparaguaya.pspserver.families.dtos;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;


/**
*
* @author mgonzalez
*
*/


public class FamilyOrganizationReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*private Map<OrganizationDTO, List<FamilyDTO>> familyByOrganization;
    
    public FamilyOrganizationReportDTO() {
        super();
    }

    public FamilyOrganizationReportDTO(Map<OrganizationDTO, List<FamilyDTO>> familyByOrganization) {
        this.familyByOrganization = familyByOrganization;
    }

    public Map<OrganizationDTO, List<FamilyDTO>> getFamilyByOrganization() {
        return familyByOrganization;
    }

    public void setFamilyByOrganization(Map<OrganizationDTO, List<FamilyDTO>> familyByOrganization) {
        this.familyByOrganization = familyByOrganization;
    }*/

    
    
    
      @NotNull private String name;
     
      private String code;
     
      private String description;
      
      private boolean isActive;
      
      private List<FamilyReportDTO> families;
      
      public FamilyOrganizationReportDTO(String name, String code, String descripcion, boolean isActive) {
          this.name = name;
          this.code = code;
          this.description = descripcion;
          this.isActive = isActive;
      }
      
      
      public String getName() { return name; }
      
      public void setName(String name) { this.name = name; }
      
      public String getCode() { return code; }
      
      public void setCode(String code) { this.code = code; }
      
      public String getDescription() { return description; }
      
      public void setDescription(String description) { this.description =
      description; }
      
      public boolean isActive() { return isActive; }
      
      public void setActive(boolean isActive) { this.isActive = isActive; }
      
      public List<FamilyReportDTO> getFamilies() { return families; }
      
      public void setFamilies(List<FamilyReportDTO> families) { this.families =
      families; }
     

}
