package com.example.service;

import com.example.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseSearchServiceImpl {
	void save (Course course);
	Page<Course> search(String name,Pageable pageable);
	Page<Course> findAll(Pageable pageable);
}
