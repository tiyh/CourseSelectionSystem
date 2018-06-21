package com.example.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Course;

@Repository
public interface CourseSearchDAO extends ElasticsearchRepository<Course,Long>{
    Page<Course> findByNameLike(String name,Pageable pageable);
}
