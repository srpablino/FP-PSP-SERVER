package py.org.fundacionparaguaya.pspserver.web.rest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import py.org.fundacionparaguaya.pspserver.PspServerApplication;
import py.org.fundacionparaguaya.pspserver.common.constants.ErrorCodes;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.services.OrganizationService;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.util.TestHelper;

/**
 * 
 *created by jaime on 9/5/17
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PspServerApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrganizationControllerIntegrationTest {

	@Autowired
    private OrganizationController controller;

    @Autowired
    private ExceptionTranslatorAdvice exceptionTranslator;

    @Autowired
    private OrganizationService organizationService;

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }
    
    @Test
    public void requestingPutOrganizationShouldAddNewOrganization() throws Exception {
    	OrganizationDTO dto = OrganizationDTO.builder()
                .name("foo.name")
                .code(new Integer(1))
                .description("foo.description")
                .isActive(true)
                .country(getCountryTest())
                .application(getApplicationTest())
                .information("foo.information")
                .build();
        String json = TestHelper.mapToJson(dto);
        mockMvc.perform(post("/api/v1/organizations").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(dto.getName())));
    }
    
    @Test
    public void requestingPostOrganizationShouldUpdateOrganization() throws Exception {
    	OrganizationDTO newDto = addNewOrganization();
        Long organizationId = newDto.getId();

        OrganizationDTO updateDto = OrganizationDTO.builder()
        		.name("foo.organization")
                .code(new Integer(1))
                .description("foo.description")
                .isActive(true)
                .country(getCountryTest())
                .application(getApplicationTest())
                .information("foo.information")
                .build();

        String json = TestHelper.mapToJson(updateDto);
        mockMvc.perform(put("/api/v1/organizations/{organizationId}", organizationId).content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updateDto.getName())));

    }
    
    private OrganizationDTO addNewOrganization() {
    	OrganizationDTO dto = OrganizationDTO.builder()
    			.name("foo.organization")
                .code(new Integer(1))
                .description("foo.description")
                .isActive(true)
                .country(getCountryTest())
                .application(getApplicationTest())
                .information("foo.information")
                .build();
        return organizationService.addOrganization(dto);
    }
    
    @Test
    public void requestingPostOrganizationShouldFailIfNotValidArgument() throws Exception {
        List<String> properties = Arrays.asList("name");
        mockMvc.perform(post("/api/v1/organizations").content("{}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorCodes.ERR_VALIDATION)))
                .andExpect(jsonPath("$.fieldErrors.[0].objectName", is("organizationDTO")))
                .andExpect(jsonPath("$.fieldErrors.[0].field", isIn(properties)))
                .andExpect(jsonPath("$.fieldErrors.[0].message").value("NotNull"));
    }
    
    
    @Test
    public void requestingGetOrganizationShouldFailIfOrganizationIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/organizations/{organizationId}", -999999))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorCodes.ERR_VALIDATION)))
                .andExpect(jsonPath("$.description").value("Argument was -999999 but expected nonnegative"));
    }
    
    private CountryDTO getCountryTest() {
		CountryDTO dto = new CountryDTO();
		dto.setCountryId(new Long(1));
		dto.setCountry("foo.COUNTRY");
		return dto;
	}
    
    private ApplicationDTO getApplicationTest() {
    	ApplicationDTO dto = new ApplicationDTO();
		dto.setApplicationId(new Long(1));
		dto.setDescription("foo.APPLICATION");
		return dto;
	}
}
