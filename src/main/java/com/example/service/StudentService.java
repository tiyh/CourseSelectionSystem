package com.example.service;

import java.util.List;

import com.example.model.Student;

public interface StudentService {
	public void addStudent(Student p);
	public void updateStudent(Student p);
	public List<Student> listStudents();
	public Student getStudentById(int id);
	public void removeStudent(int id);
}
