package com.studhub.controller.front;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.studhub.StudhubApplicationTests.TEXT_HTML_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
@TestPropertySource("/application-test.properties")
public class UserControllerTests {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @Test
    public void testUserProfilePage() {
        String baseUrl = "http://" + HOST + ":" + PORT + "/user/";
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 1; i < 14; i++) {
            ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + i, HttpMethod.GET, null, String.class);
            Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
            Assert.assertEquals(Objects.requireNonNull(responseEntity.getHeaders().getContentType()), TEXT_HTML_UTF8);
        }
    }
}
