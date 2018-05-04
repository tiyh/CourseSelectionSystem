package com.example.dao;

import java.util.List;

import com.example.model.Course;

public interface SelectionDAO {
	public List<Course> getOrderedCourse(int studentId);
	public void selectCourse(int studentId,int courseId);
	public void deleteSelectedCourse(int selectionId);
	public boolean courseOrderable(int courseId,int studentId);
}
