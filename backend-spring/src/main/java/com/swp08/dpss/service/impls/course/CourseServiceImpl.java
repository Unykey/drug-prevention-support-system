package com.swp08.dpss.service.impls.course;

import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.repository.course.CourseRepository;
import com.swp08.dpss.service.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CourseServiceImpl extends CourseService {
    CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
         courseRepository.findAll();

    }
}
