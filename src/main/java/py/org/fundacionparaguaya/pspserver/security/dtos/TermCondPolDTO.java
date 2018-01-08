package py.org.fundacionparaguaya.pspserver.security.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolType;

public class TermCondPolDTO {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("html")
    private String html;
    
    @JsonProperty("version")
    private String version;
    
    @JsonProperty("year")
    private Integer year;
    
    @JsonProperty("created_date")
    private String createdDate;
    
    @JsonProperty("type")
    private TermCondPolType type;
    
    private TermCondPolDTO(Long id, String html, String version, Integer year, String createdDate, TermCondPolType type) {
        this.id = id;
        this.html = html;
        this.version = version;
        this.year = year;
        this.createdDate = createdDate;
        this.type = type;
    }

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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public TermCondPolType getType() {
        return type;
    }

    public void setType(TermCondPolType type) {
        this.type = type;
    }
    
    public static class Builder {
        
        private Long id;
        private String html;
        private String version;
        private Integer year;
        private String createdDate;
        private TermCondPolType type;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder html(String html) {
            this.html = html;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder year(Integer year) {
            this.year = year;
            return this;
        }
        
        public Builder createdDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }
        
        public Builder type(TermCondPolType type) {
            this.type = type;
            return this;
        }

        public TermCondPolDTO build() {
            return new TermCondPolDTO(id, html, version, year, createdDate, type);
        }
    }
    
    

}
