package com.example.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Course;
import com.example.model.SearchCourse;

@Repository
public interface CourseSearchDAO extends ElasticsearchRepository<SearchCourse,Integer>{
    Page<SearchCourse> findByNameLike(String name,Pageable pageable);
}

