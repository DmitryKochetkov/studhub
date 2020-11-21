package com.studhub.dto;

import com.studhub.util.TestObject;
import com.studhub.util.TestObjectFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PageDtoTests {
    @Test
    @Transactional
    public void testUserDtoConstructor() {
        List<TestObject> content = new ArrayList<>();
        for (int i = 0; i < 19; i++)
            content.add(TestObjectFactory.generateInstance());

        PageImpl<TestObject> page = new PageImpl<>(content, PageRequest.of(0, 10), 2);
        PageDto<TestObject> pageDto = new PageDto<>(page);

        Assert.assertEquals(content, pageDto.getContent());
        Assert.assertEquals(1, pageDto.getNumber());
        Assert.assertEquals(2, pageDto.getTotalPages());
    }
}