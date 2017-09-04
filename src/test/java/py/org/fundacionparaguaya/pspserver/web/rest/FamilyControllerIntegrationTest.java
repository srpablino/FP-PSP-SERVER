package py.org.fundacionparaguaya.pspserver.web.rest;

import static org.hamcrest.CoreMatchers.is;
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
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.PersonDTO;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.util.TestHelper;

/**
 * 
 * Created by mcespedes on 9/4/17
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PspServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class FamilyControllerIntegrationTest {

	@Autowired
	private FamilyController controller;

	@Autowired
	private ExceptionTranslatorAdvice exceptionTranslator;

	@Autowired
	FamilyService familyService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setControllerAdvice(exceptionTranslator)
				.build();
	}
	
	@Test
	public void requestingPutFamilyShouldAddNewFamily() throws Exception {
		FamilyDTO dto = FamilyDTO.builder()
				.name("foo.family")
				.country(getCountryTest())
				.city(getCityTest())
				.locationType("foo.locationType")
				.locationPositionGps("foo.locationPositionGps")
				.person(getPersonTest())
				.application(getApplcationTest())
				.organization(getOrganizationTest())
				.build();
		String json = TestHelper.mapToJson(dto);
		mockMvc.perform(post("/api/v1/families").content(json).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", is(dto.getName())));
	}
	
	@Test
    public void requestingPostFamilyShouldUpdateFamily() throws Exception {
        FamilyDTO newDto = addNewFamily();
        Long familyId = newDto.getFamilyId();

        FamilyDTO updateDto = FamilyDTO.builder()
        		.name("foo.family")
				.country(getCountryTest())
				.city(getCityTest())
				.locationType("foo.locationType")
				.locationPositionGps("foo.locationPositionGps")
				.person(getPersonTest())
				.application(getApplcationTest())
				.organization(getOrganizationTest())
                .build();

        String json = TestHelper.mapToJson(updateDto);
        mockMvc.perform(put("/api/v1/families/{familyId}", familyId).content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updateDto.getName())));

    }
	
	private FamilyDTO addNewFamily() {
		FamilyDTO dto = FamilyDTO.builder()
				.name("foo.family")
				.country(getCountryTest())
				.city(getCityTest())
				.locationType("foo.locationType")
				.locationPositionGps("foo.locationPositionGps")
				.person(getPersonTest())
				.application(getApplcationTest())
				.organization(getOrganizationTest())
                .build();
        return familyService.addFamily(dto);
    }
	
	@Test
    public void requestingPostFamilyShouldFailIfNotValidArgument() throws Exception {
        List<String> properties = Arrays.asList("name");
        mockMvc.perform(post("/api/v1/families").content("{}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorCodes.ERR_VALIDATION)))
                .andExpect(jsonPath("$.fieldErrors.[0].objectName", is("familyDTO")))
                .andExpect(jsonPath("$.fieldErrors.[0].field", isIn(properties)))
                .andExpect(jsonPath("$.fieldErrors.[0].message").value("NotNull"));
    }
	
	@Test
    public void requestingGetFamilyShouldFailIfFamilyIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/families/{familyId}", -999999))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorCodes.ERR_VALIDATION)))
                .andExpect(jsonPath("$.description").value("Argument was -999999 but expected nonnegative"));
    }
	
	private OrganizationDTO getOrganizationTest() {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setOrganizationId(new Long(1));
		dto.setName("foo.ORGANIZATION");
		//...
		return dto;
	}

	private ApplicationDTO getApplcationTest() {
		ApplicationDTO dto = new ApplicationDTO();
		dto.setApplicationId(new Long(1));
		dto.setName("foo.APPLICATION");
		//...
		return dto;
	}

	private PersonDTO getPersonTest() {
		PersonDTO dto = new PersonDTO();
		dto.setPersonId(new Long(1));
		dto.setName("foo.NAME");
		dto.setLastname("foo.LASTNAME");
		//...
		return dto;
	}

	private CityDTO getCityTest() {
		CityDTO dto = new CityDTO();
		dto.setCityId(new Long(1));
		dto.setCity("foo.CITY");
		return dto;
	}

	private CountryDTO getCountryTest() {
		CountryDTO dto = new CountryDTO();
		dto.setCountryId(new Long(1));
		dto.setCountry("foo.COUNTRY");
		return dto;
	}

}
