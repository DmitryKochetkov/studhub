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

/**
 * Класс тестирования GET эндпоинтов.
 */
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

    private boolean isEndpoint(String url) {
        return url.matches("(/[-a-zA-Z0-9@:%._\\+~#=]+)+");
    }

    /**
    * Тестирование GET эндпоинтов. Проверяется, что все html-страницы, которые можно получить без
     * параметров, возвращаются с кодом 200.
    * @throws Exception в случае, если код ответа не равен 200 или ответом не является html-страница.
    * */
    @Test
    public void testViews() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String root = "http://" + HOST + ":" + PORT;
        log.info("Requests are gonna be sent to " + root);

        //automatically getting all GET endpoints here
        for (RequestMappingInfo requestMappingInfo: requestHandlerMapping.getHandlerMethods().keySet()) {
            if (requestMappingInfo.getMethodsCondition().getMethods().contains(RequestMethod.GET))
                for (String url: requestMappingInfo.getPatternsCondition().getPatterns()) {
                    if (!url.startsWith("/api") && isEndpoint(url)) {
                        try {
                            ResponseEntity<String> responseEntity = restTemplate.exchange(root + url, HttpMethod.GET, null, String.class);
                            Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
                            Assert.assertEquals(Objects.requireNonNull(responseEntity.getHeaders().getContentType()), TEXT_HTML_UTF8);
                        }
                        catch (RuntimeException e) {
                            log.info("Test failed on endpoint " + url);
                            throw e;
                        }
                        log.info("Test passed on endpoint " + url);
                    }
                }
        }
    }
}
