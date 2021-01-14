package com.studhub.service;

import com.studhub.dto.CourseStatisticsByExamSpecificationDto;
import com.studhub.dto.ProblemCodeStatisticsDto;
import com.studhub.entity.Course;
import com.studhub.entity.ExamSpecification;
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
     * @param examSpecification Спецификация для агрегации задач
     * @return DTO с описанием количества посылок по каждому типу задачи спецификации
     */
    public CourseStatisticsByExamSpecificationDto getCourseStatisticsByExamSpecification(Course course, ExamSpecification examSpecification) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery("""
            select distinct problem_code_id, count(problem_id) as all_cnt, count(case when verdict_id = 1 then 1 else null end) as ok_cnt
            from(
                select homework_problems.problem_id, code_mapping.problem_code_id, verdict_id
                from homework_problems
                inner join submissions on homework_problems.id = submissions.homework_problem_id
                inner join problem_code_mapping code_mapping on homework_problems.problem_id = code_mapping.problem_id
                inner join problem_codes code on code_mapping.problem_code_id = code.id
                inner join homework h on homework_problems.homework_id = h.id
                inner join courses c on c.id = h.course_id
                where
                      homework_id in (select id from homework where course_id = :course_id) and
                      code.specification_id = c.active_specification_id
                ) as selection
            group by problem_code_id;
            """)
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
        courseStatisticsByExamSpecificationDto.setSpecificationId(examSpecification.getId());
        return courseStatisticsByExamSpecificationDto;
    }

    //TODO: вынести сюда статистику по домашним работам
}
