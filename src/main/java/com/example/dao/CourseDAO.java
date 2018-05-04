package com.example.dao;

import java.util.List;

import com.example.model.Course;

public interface CourseDAO {
	public void addCourse(Course p);
	public void updateCourse(Course p);
	public List<Course> listCourses();
	public Course getCourseById(int id);
	public void removeCourse(int id);
}
