package py.org.fundacionparaguaya.pspserver.system.country.test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import py.org.fundacionparaguaya.pspserver.PspServerApplication;
import py.org.fundacionparaguaya.pspserver.system.country.domain.Country;
import py.org.fundacionparaguaya.pspserver.system.country.web.controller.CountryController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = PspServerApplication.class, loader = AnnotationConfigContextLoader.class)
public class CountryControllerTest {

	private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/api/v1/countries/[0-9]+";

	@InjectMocks
	CountryController controller;

	@Autowired
	WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void initTests() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	// @Test
	public void shouldHaveEmptyDB() throws Exception {
		mvc.perform(get("/api/v1/countries").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void shouldRetrieve() throws Exception {
		Country r1 = mockCountry("shouldCreateRetrieveDelete");

		long countryId = 1;

		// RETRIEVE
		mvc.perform(get("/api/v1/countries/" + countryId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.countryid", is((int) countryId)))
				.andExpect(jsonPath("$.country", is(r1.getCountry())));

		// RETRIEVE should fail
		mvc.perform(get("/api/v1/countries/" + countryId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	public void shouldCreateAndUpdateAndDelete() throws Exception {
		long countryId = 1;

		Country r2 = mockCountry("shouldCreateAndUpdate2");
		r2.setCountryId(countryId);

		// RETRIEVE updated
		mvc.perform(get("/api/v1/countries/" + countryId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.countryId", is((int) countryId)))
				.andExpect(jsonPath("$.country", is(r2.getCountry())));

	}

	/*
	******************************
	 */

	private Country mockCountry(String prefix) {
		Country r = new Country();
		r.setCountry(prefix + "_country");
		return r;
	}

}
