package com.studhub.front;

import com.studhub.StudhubApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ViewTests {
    @Autowired
    private RequestMappingHandlerMapping requestHandlerMapping;

    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @Test
    public void testViews() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String root = "http://" + HOST + ":" + PORT;
        System.out.println(root);
        for (RequestMappingInfo requestMappingInfo: requestHandlerMapping.getHandlerMethods().keySet()) {
            if (requestMappingInfo.getMethodsCondition().getMethods().contains(RequestMethod.GET))
                for (String path: requestMappingInfo.getPatternsCondition().getPatterns()) {
                    if (!path.startsWith("/api")) {
                        System.out.println("Trying to get " + path);
                        ResponseEntity<String> responseEntity = restTemplate.exchange(root + path, HttpMethod.GET, null, String.class, 1);
                        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
                    }
                }
        }
    }

}
