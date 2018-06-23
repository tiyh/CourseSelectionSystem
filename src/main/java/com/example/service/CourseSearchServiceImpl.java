
package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dao.CourseSearchDAO;
import com.example.model.Course;
import com.example.model.SearchCourse;
@Service
public class CourseSearchServiceImpl implements CourseSearchService {

    @Autowired
    private CourseSearchDAO courseSearchDAO;

    @Override
    public void save(SearchCourse course) {
        courseSearchDAO.save(course);
    }

    @Override
    public Page<SearchCourse> findByName(String name, Pageable pageable) {
        return courseSearchDAO.findByNameLike(name,pageable);
    }

    @Override
    public Page<SearchCourse> findAll(Pageable pageable) {
        return courseSearchDAO.findAll(pageable);
    }

}

