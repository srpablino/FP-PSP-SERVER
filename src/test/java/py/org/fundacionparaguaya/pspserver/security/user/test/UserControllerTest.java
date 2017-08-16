package py.org.fundacionparaguaya.pspserver.security.user.test;

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
import py.org.fundacionparaguaya.pspserver.security.user.domain.User;
import py.org.fundacionparaguaya.pspserver.security.user.web.controller.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = PspServerApplication.class, loader = AnnotationConfigContextLoader.class)
public class UserControllerTest {

	private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/api/v1/users/[0-9]+";

	@InjectMocks
	UserController controller;

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
        mvc.perform(get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
    
    
    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
        User r1 = mockUser("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);

        //CREATE
        MvcResult result = mvc.perform(post("/api/v1/users")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long userid = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //RETRIEVE
        mvc.perform(get("/api/v1/users/" + userid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userid", is((int) userid)))
                .andExpect(jsonPath("$.username", is(r1.getUsername())))
                .andExpect(jsonPath("$.pass", is(r1.getPass())))
                .andExpect(jsonPath("$.active", is(r1.isActive())));

        //DELETE
        mvc.perform(delete("/api/v1/users/" + userid))
                .andExpect(status().isNoContent());

        //RETRIEVE should fail
        mvc.perform(get("/api/v1/users/" + userid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    //@Test
    public void shouldCreateAndUpdateAndDelete() throws Exception {
        User r1 = mockUser("shouldCreateAndUpdate");
        byte[] r1Json = toJson(r1);
        //CREATE
        MvcResult result = mvc.perform(post("/api/v1/users")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long userid = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        User r2 = mockUser("shouldCreateAndUpdate2");
        r2.setUserId(userid);
        byte[] r2Json = toJson(r2);

        //UPDATE
        result = mvc.perform(put("/api/v1/users/" + userid)
                .content(r2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        //RETRIEVE updated
        mvc.perform(get("/api/v1/users/" + userid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userid", is((int) userid)))
                .andExpect(jsonPath("$.username", is(r2.getUsername())))
                .andExpect(jsonPath("$.pass", is(r2.getPass())))
                .andExpect(jsonPath("$.active", is(r2.isActive())));

        //DELETE
        mvc.perform(delete("/api/v1/users/" + userid))
                .andExpect(status().isNoContent());
    }


    /*
    ******************************
     */

    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


    private User mockUser(String prefix) {
        User r = new User();
        r.setUsername(prefix+"_username");
        r.setPass(prefix+"_password");
        r.setActive(true);
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    // match redirect header URL (aka Location header)
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }

}
