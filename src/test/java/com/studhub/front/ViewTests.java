package com.studhub.front;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ViewTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RequestMappingHandlerMapping requestHandlerMapping;

    @Before
    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void testViews() throws Exception {
        for (RequestMappingInfo requestMappingInfo: requestHandlerMapping.getHandlerMethods().keySet()) {
            if (requestMappingInfo.getMethodsCondition().getMethods().contains(RequestMethod.GET))
                for (String path: requestMappingInfo.getPatternsCondition().getPatterns())
                    mockMvc.perform(MockMvcRequestBuilders.get(path, 1))
                        .andExpect(status().isOk())
                        .andReturn();
        }
    }

}
