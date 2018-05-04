package com.example.service;

import java.util.List;

import com.example.model.Course;

public interface CourseService {
	public void addCourse(Course c);
	public void updateCourse(Course c);
	public List<Course> listCourses();
	public Course getCourseById(int id);
	public void removeCourse(int id);


}
