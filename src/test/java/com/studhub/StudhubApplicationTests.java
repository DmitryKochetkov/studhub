package com.studhub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class StudhubApplicationTests {
  
    @Test
    void contextLoads() {
    }
  
}
