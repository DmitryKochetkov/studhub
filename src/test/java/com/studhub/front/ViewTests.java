package com.studhub.front;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Objects;

import static com.studhub.StudhubApplicationTests.TEXT_HTML_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Slf4j
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
        log.info("Requests are gonna be sent to " + root);

        //automatically getting all GET endpoints here
        for (RequestMappingInfo requestMappingInfo: requestHandlerMapping.getHandlerMethods().keySet()) {
            if (requestMappingInfo.getMethodsCondition().getMethods().contains(RequestMethod.GET))
                for (String endpoint: requestMappingInfo.getPatternsCondition().getPatterns()) {
                    if (!endpoint.startsWith("/api")) {
                        ResponseEntity<String> responseEntity = restTemplate.exchange(root + endpoint, HttpMethod.GET, null, String.class, 1);
                        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
                        Assert.assertEquals(Objects.requireNonNull(responseEntity.getHeaders().getContentType()), TEXT_HTML_UTF8);
                        log.info("Test passed on endpoint " + endpoint);
                        //TODO: log if assertion fails
                    }
                }
        }
    }
}
