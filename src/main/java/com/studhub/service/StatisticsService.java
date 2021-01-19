package com.studhub.service;

import com.studhub.dto.CourseStatisticsByExamSpecificationDto;
import com.studhub.dto.ProblemCodeStatisticsDto;
import com.studhub.entity.Course;
import com.studhub.entity.Specification;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Service
public class StatisticsService {
    @Autowired
    private EntityManager entityManager;

    /**
     *
     * @param course Курс, для которого считается статистика
     * @param specification Спецификация для агрегации задач
     * @return CourseStatisticsByExamSpecificationDto с описанием количества посылок по каждому типу задачи спецификации
     */
    public CourseStatisticsByExamSpecificationDto getCourseStatisticsBySpecification(Course course, Specification specification) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery("select distinct problem_code_id, count(problem_id) as all_cnt, count(case when verdict_id = 1 then 1 else null end) as ok_cnt\n" +
                "            from(\n" +
                "                select homework_problems.problem_id, code_mapping.problem_code_id, verdict_id\n" +
                "                from homework_problems\n" +
                "                inner join submissions on homework_problems.id = submissions.homework_problem_id\n" +
                "                inner join problem_code_mapping code_mapping on homework_problems.problem_id = code_mapping.problem_id\n" +
                "                inner join problem_codes code on code_mapping.problem_code_id = code.id\n" +
                "                inner join homework h on homework_problems.homework_id = h.id\n" +
                "                inner join courses c on c.id = h.course_id\n" +
                "                where\n" +
                "                      homework_id in (select id from homework where course_id = :course_id) and\n" +
                "                      code.specification_id = c.active_specification_id\n" +
                "                ) as selection\n" +
                "            group by problem_code_id;")
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        ProblemCodeStatisticsDto problemCodeStatisticsDto = new ProblemCodeStatisticsDto();
                        problemCodeStatisticsDto.setProblemCodeId(((BigInteger)objects[0]).intValue());
                        problemCodeStatisticsDto.setTotalSubmissions(((BigInteger)objects[1]).intValue());
                        problemCodeStatisticsDto.setCorrectSubmissions(((BigInteger)objects[2]).intValue());
                        return problemCodeStatisticsDto;
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                });

        query.setParameter("course_id", course.getId());

        List<ProblemCodeStatisticsDto> statistics = query.getResultList();

        CourseStatisticsByExamSpecificationDto courseStatisticsByExamSpecificationDto = new CourseStatisticsByExamSpecificationDto();
        courseStatisticsByExamSpecificationDto.setStatistics(statistics);
        courseStatisticsByExamSpecificationDto.setCourseId(course.getId());
        courseStatisticsByExamSpecificationDto.setSpecificationId(specification.getId());
        return courseStatisticsByExamSpecificationDto;
    }

    //TODO: вынести сюда статистику по домашним работам
}
