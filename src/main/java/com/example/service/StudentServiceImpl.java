package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.example.dao.StudentDAO;
import com.example.model.Student;

@Service
public class StudentServiceImpl implements StudentService{
	private StudentDAO studentDAO;
    @Autowired
	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	@Override
	@Caching(cacheable=@Cacheable(value = "studentcache", keyGenerator = "wiselyKeyGenerator"),evict = @CacheEvict(value = "liststudentcache", allEntries = true))
	public void addStudent(Student p) {
		this.studentDAO.addStudent(p);
	}

	@Override
	@Caching(put = @CachePut("studentcache"), evict = @CacheEvict(value = "liststudentcache", allEntries = true))
	
	public void updateStudent(Student p) {
		this.studentDAO.updateStudent(p);
	}

	@Override
	@Cacheable(value = "liststudentcache",keyGenerator = "wiselyKeyGenerator")
	public List<Student> listStudents() {
		return this.studentDAO.listStudents();
	}

	@Override
	@Cacheable(value = "studentcache", keyGenerator = "wiselyKeyGenerator")
	public Student getStudentById(int id) {
		return this.studentDAO.getStudentById(id);
	}

	@Override
	@Caching(evict = { @CacheEvict("studentcache"),@CacheEvict(value = "liststudentcache", allEntries = true) })
	public void removeStudent(int id) {
		this.studentDAO.removeStudent(id);
	}
}
