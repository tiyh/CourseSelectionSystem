package com.example.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.model.Student;

public interface StudentService {
	public Student addStudent(Student p);
	public void updateStudent(Student p);
	public List<Student> listStudents();
	public Student getStudentById(int id);
	public void removeStudent(int id);
	public String refresh(String oldToken);
	public JSONObject login(String username, String password);
}
