package com.example.service;

import com.example.model.Course;
import com.example.model.SearchCourse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseSearchService {
	void save (SearchCourse course);
	Page<SearchCourse> findByCourseName(String name,Pageable pageable);
	Page<SearchCourse> findAll(Pageable pageable);
}