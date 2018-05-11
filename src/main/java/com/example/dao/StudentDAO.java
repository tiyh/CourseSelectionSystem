package com.example.dao;

import java.util.List;

import com.example.model.Student;

public interface StudentDAO {
	public Student addStudent(Student p);
	public void updateStudent(Student p);
	public List<Student> listStudents();
	public Student getStudentById(int id);
	public Student getStudentByUsername(String name);
	public void removeStudent(int id);
}
