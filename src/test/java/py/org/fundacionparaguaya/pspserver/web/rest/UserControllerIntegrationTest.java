package py.org.fundacionparaguaya.pspserver.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.isIn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.PspServerApplication;
import py.org.fundacionparaguaya.pspserver.security.services.UserService;
import py.org.fundacionparaguaya.pspserver.common.constants.ErrorCodes;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDTO;
import py.org.fundacionparaguaya.pspserver.util.TestHelper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by rodrigovillalba on 8/27/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PspServerApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private UserController controller;

    @Autowired
    private ExceptionTranslatorAdvice exceptionTranslator;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    public void requestingPutUserShouldAddNewUser() throws Exception {
        UserDTO dto = UserDTO.builder()
                .username("foo.user")
                .pass("123")
                .build();
        String json = TestHelper.mapToJson(dto);
        mockMvc.perform(post("/api/v1/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(dto.getUsername())));
    }

    @Test
    public void requestingPostUserShouldUpdateUser() throws Exception {
        UserDTO newDto = addNewUser();
        Long userId = newDto.getUserId();

        UserDTO updateDto = UserDTO.builder()
                .username("bar.user")
                .pass("123")
                .build();

        String json = TestHelper.mapToJson(updateDto);
        mockMvc.perform(put("/api/v1/users/{userId}", userId).content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(updateDto.getUsername())));

    }

    private UserDTO addNewUser() {
        UserDTO dto = UserDTO.builder()
                .username("foo.user")
                .pass("123")
                .build();
        return userService.addUser(dto);
    }

    @Test
    public void requestingPostUserShouldFailIfNotValidArgument() throws Exception {
        List<String> properties = Arrays.asList("username", "pass");
        mockMvc.perform(post("/api/v1/users").content("{}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorCodes.ERR_VALIDATION)))
                .andExpect(jsonPath("$.fieldErrors.[0].objectName", is("userDTO")))
                .andExpect(jsonPath("$.fieldErrors.[0].field", isIn(properties)))
                .andExpect(jsonPath("$.fieldErrors.[0].message").value("NotNull"))
                .andExpect(jsonPath("$.fieldErrors.[1].objectName", is("userDTO")))
                .andExpect(jsonPath("$.fieldErrors.[1].field", isIn(properties)))
                .andExpect(jsonPath("$.fieldErrors.[1].message").value("NotNull"));
    }


    @Test
    public void requestingPostUserShouldFailIfUserAlreadyExists() throws Exception {
        UserDTO dto = UserDTO.builder()
                .username("admin")
                .pass("123")
                .build();
        String json = TestHelper.mapToJson(dto);
        mockMvc.perform(post("/api/v1/users").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User already exists."))
                .andExpect(jsonPath("$.params.username").value("admin"));
    }



    @Test
    public void requestingGetUserShouldFailIfUserIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/users/{userId}", -999999))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorCodes.ERR_VALIDATION)))
                .andExpect(jsonPath("$.description").value("Argument was -999999 but expected nonnegative"));
    }
}
