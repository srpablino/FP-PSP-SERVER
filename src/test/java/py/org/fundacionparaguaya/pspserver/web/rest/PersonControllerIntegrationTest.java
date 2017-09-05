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
import py.org.fundacionparaguaya.pspserver.families.constants.Gender;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.PersonDTO;
import py.org.fundacionparaguaya.pspserver.families.services.PersonService;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
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
public class PersonControllerIntegrationTest {

	@Autowired
    private PersonController controller;

    @Autowired
    private ExceptionTranslatorAdvice exceptionTranslator;

    @Autowired
    private PersonService personService;

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }
    
    @Test
    public void requestingPutPersonShouldAddNewPerson() throws Exception {
    	PersonDTO dto = PersonDTO.builder()
                .name("foo.name")
                .lastname("foo.lastname")
                .identificationType("foo.identificationType")
                .identificationNumber("foo.identificationNumber")
                .personRole("foo.personRole")
                .gender(Gender.M)
                .activityPrimary("foo.activityPrimary")
                .activitySecundary("foo.activitySecundary")
                .country(getCountryTest())
                .city(getCityTest())
                .family(getFamilynTest())
                .build();

        String json = TestHelper.mapToJson(dto);
        mockMvc.perform(post("/api/v1/people").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(dto.getName())));
    }
    
    @Test
    public void requestingPostPersonShouldUpdatePerson() throws Exception {
    	PersonDTO newDto = addNewPerson();
        Long personId = newDto.getPersonId();

        PersonDTO updateDto = PersonDTO.builder()
        		 .name("foo.person")
                 .lastname("foo.lastname")
                 .identificationType("foo.identificationType")
                 .identificationNumber("foo.identificationNumber")
                 .personRole("foo.personRole")
                 .gender(Gender.M)
                 .activityPrimary("foo.activityPrimary")
                 .activitySecundary("foo.activitySecundary")
                 .country(getCountryTest())
                 .city(getCityTest())
                 .family(getFamilynTest())
                 .build();

        String json = TestHelper.mapToJson(updateDto);
        mockMvc.perform(put("/api/v1/people/{personId}", personId).content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updateDto.getName())));

    }
    
    private PersonDTO addNewPerson() {
    	PersonDTO dto = PersonDTO.builder()
    			 .name("foo.person")
                 .lastname("foo.lastname")
                 .identificationType("foo.identificationType")
                 .identificationNumber("foo.identificationNumber")
                 .personRole("foo.personRole")
                 .gender(Gender.M)
                 .activityPrimary("foo.activityPrimary")
                 .activitySecundary("foo.activitySecundary")
                 .country(getCountryTest())
                 .city(getCityTest())
                 .family(getFamilynTest())
                 .build();
        return personService.addPerson(dto);
    }
    
    @Test
    public void requestingPostPersonShouldFailIfNotValidArgument() throws Exception {
        List<String> properties = Arrays.asList("name");
        mockMvc.perform(post("/api/v1/people").content("{}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorCodes.ERR_VALIDATION)))
                .andExpect(jsonPath("$.fieldErrors.[0].objectName", is("personDTO")))
                .andExpect(jsonPath("$.fieldErrors.[0].field", isIn(properties)))
                .andExpect(jsonPath("$.fieldErrors.[0].message").value("NotNull"));
    }
    
    
    @Test
    public void requestingGetPersonShouldFailIfPersonIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/people/{personId}", -999999))
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
    
    private FamilyDTO getFamilynTest() {
    	FamilyDTO dto = new FamilyDTO();
		dto.setFamilyId(new Long(1));
		dto.setName("foo.FAMILY");
		return dto;
	}
    
    private CityDTO getCityTest() {
		CityDTO dto = new CityDTO();
		dto.setCityId(new Long(1));
		dto.setCity("foo.CITY");
		return dto;
	}
}
