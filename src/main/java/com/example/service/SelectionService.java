package com.example.service;

import java.util.List;

import com.example.model.Course;

public interface SelectionService {
	public List<Course> getOrderedCourse(int studentId);
	public boolean selectCourse(int studentId,int courseId);
	public void deleteSelectedCourse(int selectionId);
	public boolean courseOrderable(int courseId,int studentId);
}
