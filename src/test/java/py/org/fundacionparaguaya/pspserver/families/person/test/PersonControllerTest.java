package py.org.fundacionparaguaya.pspserver.families.person.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import py.org.fundacionparaguaya.pspserver.PspServerApplication;
import py.org.fundacionparaguaya.pspserver.families.person.domain.Gender;
import py.org.fundacionparaguaya.pspserver.families.person.domain.Person;
import py.org.fundacionparaguaya.pspserver.families.person.web.controller.PersonController;
import py.org.fundacionparaguaya.pspserver.system.city.domain.City;
import py.org.fundacionparaguaya.pspserver.system.country.domain.Country;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = PspServerApplication.class, loader = AnnotationConfigContextLoader.class)
public class PersonControllerTest {

	private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/api/v1/people/[0-9]+";

	@InjectMocks
	PersonController controller;

	@Autowired
	WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void initTests() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
//	@Test
    public void shouldHaveEmptyDB() throws Exception {
        mvc.perform(get("/api/v1/people")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
    
    
    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
        Person r1 = mockPerson("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);

        //CREATE
        MvcResult result = mvc.perform(post("/api/v1/people")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long personId = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //RETRIEVE
        mvc.perform(get("/api/v1/people/" + personId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personid", is((int) personId)))
                .andExpect(jsonPath("$.name", is(r1.getName())))
                .andExpect(jsonPath("$.identificationType", is(r1.getIdentificationType())))
                .andExpect(jsonPath("$.identificationNumber", is(r1.getIdentificationNumber())))
                .andExpect(jsonPath("$.personRole", is(r1.getPersonRole())))
                .andExpect(jsonPath("$.gender", is(r1.getGender())))
                .andExpect(jsonPath("$.activityPrimary", is(r1.getActivityPrimary())))
                .andExpect(jsonPath("$.activitySecundary", is(r1.getActivitySecundary())))
                .andExpect(jsonPath("$.phoneNumber", is(r1.getPhoneNumber())))
                .andExpect(jsonPath("$.country", is(r1.getCountry())))
                .andExpect(jsonPath("$.city", is(r1.getCity())))
//                .andExpect(jsonPath("$.family_id", is(r1.getFamily())))
                .andExpect(jsonPath("$.lastname", is(r1.getLastname())));

        //DELETE
        mvc.perform(delete("/api/v1/people/" + personId))
                .andExpect(status().isNoContent());

        //RETRIEVE should fail
        mvc.perform(get("/api/v1/people/" + personId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    //@Test
    public void shouldCreateAndUpdateAndDelete() throws Exception {
    	Person r1 = mockPerson("shouldCreateAndUpdate");
        byte[] r1Json = toJson(r1);
        //CREATE
        MvcResult result = mvc.perform(post("/api/v1/people")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long personId = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        Person r2 = mockPerson("shouldCreateAndUpdate2");
        r2.setPersonId(personId);
        byte[] r2Json = toJson(r2);

        //UPDATE
        result = mvc.perform(put("/api/v1/people/" + personId)
                .content(r2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        //RETRIEVE updated
        mvc.perform(get("/api/v1/people/" + personId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personid", is((int) personId)))
                .andExpect(jsonPath("$.name", is(r1.getName())))
                .andExpect(jsonPath("$.identificationType", is(r1.getIdentificationType())))
                .andExpect(jsonPath("$.identificationNumber", is(r1.getIdentificationNumber())))
                .andExpect(jsonPath("$.personRole", is(r1.getPersonRole())))
                .andExpect(jsonPath("$.gender", is(r1.getGender())))
                .andExpect(jsonPath("$.activityPrimary", is(r1.getActivityPrimary())))
                .andExpect(jsonPath("$.activitySecundary", is(r1.getActivitySecundary())))
                .andExpect(jsonPath("$.phoneNumber", is(r1.getPhoneNumber())))
                .andExpect(jsonPath("$.country", is(r1.getCountry())))
                .andExpect(jsonPath("$.city", is(r1.getCity())))
//                .andExpect(jsonPath("$.family_id", is(r1.getFamily())))
                .andExpect(jsonPath("$.lastname", is(r1.getLastname())));

        //DELETE
        mvc.perform(delete("/api/v1/people/" + personId))
                .andExpect(status().isNoContent());
    }


    /*
    ******************************
     */

    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


    private Person mockPerson(String prefix) {
    	Person r = new Person();
        r.setName(prefix+"_name");
        r.setLastname(prefix+"lasname");
        r.setIdentificationType(prefix+"identificationType");
        r.setIdentificationNumber(prefix+"identificationNumber");
        r.setPersonRole(prefix+"personRole");
        r.setGender(Gender.F);
        r.setActivityPrimary(prefix+"activityPrimary");
        r.setActivitySecundary(prefix+"activitySecundary");
        r.setPhoneNumber(prefix+"phoneNumber");
        r.setCountry(new Country(new Long(1),"PY"));
        r.setCity(new City(new Long(1),"ASUNCION"));
//        r.setFamily(new Family();
        
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }

}
