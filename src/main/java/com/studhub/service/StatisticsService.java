package com.studhub.service;

import com.studhub.dto.CourseStatisticsBySpecificationDto;
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
    public CourseStatisticsBySpecificationDto getCourseStatisticsBySpecification(Course course, Specification specification) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery("select problem_codes.id, description, index_in_specification, specification_id, count(verdict_id) as all_submissions_cnt, count(case when verdict_id = 1 then 1 else null end) as correct_submissions_cnt from problem_codes\n" +
                "    full outer join problem_code_mapping pcm on problem_codes.id = pcm.problem_code_id\n" +
                "    left join homework_problems hp on pcm.problem_id = hp.problem_id\n" +
                "    left join submissions s on hp.id = s.homework_problem_id\n" +
                "where specification_id in (select active_specification_id from courses where id = :course_id)\n" +
                "group by description, index_in_specification, specification_id, problem_codes.id\n" +
                "order by index_in_specification;")
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        ProblemCodeStatisticsDto problemCodeStatisticsDto = new ProblemCodeStatisticsDto();
                        problemCodeStatisticsDto.setProblemCodeId(((BigInteger)objects[0]).intValue());
                        problemCodeStatisticsDto.setIndexInSpecification((Integer)objects[2]);
                        problemCodeStatisticsDto.setTotalSubmissions(((BigInteger)objects[4]).intValue());
                        problemCodeStatisticsDto.setCorrectSubmissions(((BigInteger)objects[5]).intValue());
                        return problemCodeStatisticsDto;
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                });

        query.setParameter("course_id", course.getId());

        List<ProblemCodeStatisticsDto> statistics = query.getResultList();

        CourseStatisticsBySpecificationDto courseStatisticsBySpecificationDto = new CourseStatisticsBySpecificationDto();
        courseStatisticsBySpecificationDto.setData(statistics);
        courseStatisticsBySpecificationDto.setCourseId(course.getId());
        courseStatisticsBySpecificationDto.setSpecificationId(specification.getId());
        return courseStatisticsBySpecificationDto;
    }

    //TODO: вынести сюда статистику по домашним работам
}
