package com.studhub.service;

import com.studhub.dto.CourseStatisticsBySpecificationDto;
import com.studhub.dto.ProblemCodeStatisticsDto;
import com.studhub.entity.Course;
import com.studhub.entity.Specification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
public class StatisticsServiceTests {
    @Autowired
    private StatisticsService statisticsService;

    @Transactional
    @Test
    public void testGetCourseStatisticsByExamSpecification() {
        Course course = new Course();
        course.setId(1L);

        Specification specification = new Specification();
        specification.setId(4L);

        CourseStatisticsBySpecificationDto courseStatisticsBySpecificationDto = statisticsService.getCourseStatisticsBySpecification(course, specification);
        Assert.assertEquals(4L, courseStatisticsBySpecificationDto.getSpecificationId().longValue());
        Assert.assertEquals(1L, courseStatisticsBySpecificationDto.getCourseId().longValue());

        ProblemCodeStatisticsDto expectedProblemCodeStatisticsDto = new ProblemCodeStatisticsDto();
        expectedProblemCodeStatisticsDto.setProblemCodeId(14);
        expectedProblemCodeStatisticsDto.setIndexInSpecification(11);
        expectedProblemCodeStatisticsDto.setTotalSubmissions(2);
        expectedProblemCodeStatisticsDto.setCorrectSubmissions(1);

        for (ProblemCodeStatisticsDto element: courseStatisticsBySpecificationDto.getData()) {
            if (element.getProblemCodeId().equals(expectedProblemCodeStatisticsDto.getProblemCodeId())) {
                Assert.assertEquals(expectedProblemCodeStatisticsDto, element);
            }
            else {
                Assert.assertEquals(0, element.getTotalSubmissions().intValue());
                Assert.assertEquals(0, element.getCorrectSubmissions().intValue());
            }
        }
    }
}
