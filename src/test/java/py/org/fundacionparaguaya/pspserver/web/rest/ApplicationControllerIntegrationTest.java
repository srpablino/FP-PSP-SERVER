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
import py.org.fundacionparaguaya.pspserver.network.services.ApplicationService;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.util.TestHelper;

/**
 * 
 * created by mcespedes on 9/4/17
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PspServerApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ApplicationControllerIntegrationTest {

	@Autowired
    private ApplicationController controller;

    @Autowired
    private ExceptionTranslatorAdvice exceptionTranslator;

    @Autowired
    private ApplicationService applicationService;

    private MockMvc mockMvc;
	
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }
    
    @Test
    public void requestingPutApplicationShouldAddNewApplication() throws Exception {
    	ApplicationDTO dto = ApplicationDTO.builder()
                .name("foo.name")
                .code("foo.code")
                .description("foo.description")
                .isActive(true)
                .country(getCountryTest())
                .city(getCityTest())
                .information("foo.information")
                .isHub(true)
                .isOrganization(true)
                .build();
        String json = TestHelper.mapToJson(dto);
        mockMvc.perform(post("/api/v1/applications").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(dto.getName())));
    }
    
    @Test
    public void requestingPostApplicationShouldUpdateApplication() throws Exception {
    	ApplicationDTO newDto = addNewApplication();
        Long applicationId = newDto.getApplicationId();

        ApplicationDTO updateDto = ApplicationDTO.builder()
        		.name("foo.application")
                .code("foo.code")
                .description("foo.description")
                .isActive(true)
                .country(getCountryTest())
                .city(getCityTest())
                .information("foo.information")
                .isHub(true)
                .isOrganization(true)
                .build();

        String json = TestHelper.mapToJson(updateDto);
        mockMvc.perform(put("/api/v1/applications/{applicationId}", applicationId).content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updateDto.getName())));

    }
    
    private ApplicationDTO addNewApplication() {
    	ApplicationDTO dto = ApplicationDTO.builder()
                .name("foo.application")
                .code("foo.code")
                .description("foo.description")
                .isActive(true)
                .country(getCountryTest())
                .city(getCityTest())
                .information("foo.information")
                .isHub(true)
                .isOrganization(true)
                .build();
        return applicationService.addApplication(dto);
    }
    
    @Test
    public void requestingPostApplicationShouldFailIfNotValidArgument() throws Exception {
        List<String> properties = Arrays.asList("name");
        mockMvc.perform(post("/api/v1/applications").content("{}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorCodes.ERR_VALIDATION)))
                .andExpect(jsonPath("$.fieldErrors.[0].objectName", is("applicationDTO")))
                .andExpect(jsonPath("$.fieldErrors.[0].field", isIn(properties)))
                .andExpect(jsonPath("$.fieldErrors.[0].message").value("NotNull"));
    }
    
    
    @Test
    public void requestingGetApplicationShouldFailIfApplicationIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/applications/{applicationId}", -999999))
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
    
    private CityDTO getCityTest() {
		CityDTO dto = new CityDTO();
		dto.setCityId(new Long(1));
		dto.setCity("foo.CITY");
		return dto;
	}
    
}
