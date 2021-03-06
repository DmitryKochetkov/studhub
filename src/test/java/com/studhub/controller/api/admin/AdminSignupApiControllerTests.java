package com.studhub.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.studhub.payload.SignupRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.studhub.StudhubApplicationTests.APPLICATION_JSON_UTF8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
public class AdminSignupApiControllerTests {
    @Autowired
    private MockMvc mockMvc;
    
    private final String adminSignupEndpoint = "/api/admin/signup";

    @Before
    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void testGetSignup405() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(adminSignupEndpoint))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void testPostSignup201() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("Peter");
        signupRequest.setLastName("Parker");
        signupRequest.setRole("USER");
        signupRequest.setUsername("spider_man");
        signupRequest.setPassword("i_love_MJ");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(signupRequest);

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.post(adminSignupEndpoint).contentType(APPLICATION_JSON_UTF8).content(requestBody))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JSONObject jsonObject = new JSONObject(responseBody);
        long id = jsonObject.getLong("id");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostSignup400IncorrectRole() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("Norman");
        signupRequest.setLastName("Osborn");
        signupRequest.setUsername("green_goblin");
        signupRequest.setPassword("i_love_pumpkin");
        signupRequest.setRole("NOT_EXISTING");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(signupRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(adminSignupEndpoint).contentType(APPLICATION_JSON_UTF8).content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.detail").value("Bad Request"))
                .andReturn();
    }

    @Test
    public void testPostSignup400IncorrectJSONBody() throws Exception {
        String requestBody = "just_a_string";
        mockMvc.perform(MockMvcRequestBuilders.post(adminSignupEndpoint).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(adminSignupEndpoint).contentType(APPLICATION_JSON_UTF8).content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testPostSignup415() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(adminSignupEndpoint))
                .andExpect(status().isUnsupportedMediaType())
                .andReturn();
    }
}
