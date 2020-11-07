package com.studhub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
@TestPropertySource("/application-test.properties")
class StudhubApplicationTests {
  
    @Test
    void contextLoads() {
    }
  
}
