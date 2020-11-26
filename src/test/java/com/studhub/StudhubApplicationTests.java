package com.studhub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import java.nio.charset.Charset;

@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
@TestPropertySource("/application-test.properties")
public class StudhubApplicationTests {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    public static final MediaType TEXT_HTML_UTF8 =  new MediaType(MediaType.TEXT_HTML.getType(), MediaType.TEXT_HTML.getSubtype(), Charset.forName("utf8"));

    @Test
    public void main() {
        StudhubApplication.main(new String[] {});
    }
  
}
