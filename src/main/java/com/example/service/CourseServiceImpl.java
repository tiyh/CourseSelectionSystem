package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.example.dao.CourseDAO;

import com.example.model.Course;
@Service
public class CourseServiceImpl implements CourseService{

	private CourseDAO CourseDAO;
    @Autowired
	public void setCourseDAO(CourseDAO CourseDAO) {
		this.CourseDAO = CourseDAO;
	}
	
	@Override
	@Caching(cacheable = @Cacheable(value ="coursecache",keyGenerator="wiselyKeyGenerator"), evict = @CacheEvict("listcoursecache"))
	public void addCourse(Course c) {
		this.CourseDAO.addCourse(c);
		
	}

	@Override
	@Caching(put = @CachePut("coursecache"), evict = @CacheEvict("listcoursecache"))
	public void updateCourse(Course c) {
		this.CourseDAO.updateCourse(c);
		
	}

	@Override
	@Cacheable(value = "listcoursecache",keyGenerator="wiselyKeyGenerator")
	public List<Course> listCourses() {
		return this.CourseDAO.listCourses();
	}

	@Override
	@Cacheable(value = "coursecache", keyGenerator = "wiselyKeyGenerator")
	public Course getCourseById(int id) {
		return this.CourseDAO.getCourseById(id);
	}

	@Override
	@Caching(evict = { @CacheEvict("coursecache"),@CacheEvict(value = "listcoursecache", allEntries = true) })
	public void removeCourse(int id) {
		this.CourseDAO.removeCourse(id);
		
	}
	

}
