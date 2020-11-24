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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.studhub.StudhubApplicationTests.TEXT_HTML_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
@TestPropertySource("/application-test.properties")
//
public class AdminUsersControllerTests {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @Test
    public void usersPagesLoad() {
        String baseUrl = "http://" + HOST + ":" + PORT + "/admin/users?page=";
        RestTemplate restTemplate = new RestTemplate();
        List<String> urlsOk = new ArrayList<>();
        urlsOk.add(baseUrl + 1);
        urlsOk.add(baseUrl + 2);
        for (String url: urlsOk) {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
            Assert.assertEquals(Objects.requireNonNull(responseEntity.getHeaders().getContentType()), TEXT_HTML_UTF8);
        }
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void usersPageBadRequest() {
        String baseUrl = "http://" + HOST + ":" + PORT + "/admin/users?page=";
        RestTemplate restTemplate = new RestTemplate();
        List<String> urlsBadRequest = new ArrayList<>();
        urlsBadRequest.add(baseUrl + "-1");
        urlsBadRequest.add(baseUrl + "0");
        urlsBadRequest.add(baseUrl + "string");
        for (String url: urlsBadRequest) {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
            Assert.assertEquals(TEXT_HTML_UTF8, Objects.requireNonNull(responseEntity.getHeaders().getContentType()));
        }
    }
}
