package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dao.CourseSearchDAO;
import com.example.model.Course;
@Service
public class CourseSearchService implements CourseSearchServiceImpl {

    @Autowired
    private CourseSearchDAO courseSearchDAO;

    @Override
    public void save(Course course) {
        courseSearchDAO.save(course);
    }

    @Override
    public Page<Course> search(String name, Pageable pageable) {
        return courseSearchDAO.findByNameLike(name,pageable);
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseSearchDAO.findAll(pageable);
    }

}
