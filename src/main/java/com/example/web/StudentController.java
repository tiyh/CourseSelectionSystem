package com.example.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.StudentDAOImpl;
import com.example.exception.StudentNotFoundException;
import com.example.model.Student;
import com.example.service.StudentService;

@RestController
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);
	private StudentService studentService;
	@Autowired(required=true)
	public void setStudentService(StudentService ss){
		studentService = ss;
	}
    @RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
    String index(){
        return "Hello course selection!";
    }
	
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public List<?> getStudentsInJSON() {
		return studentService.listStudents();
	}
	

	@RequestMapping(value = "/student/{studentId}", method = RequestMethod.GET)
	public Student getStudentById(@PathVariable() Integer studentId) {
		Student student = studentService.getStudentById(studentId);
		if(student==null) throw new StudentNotFoundException();
		return student;
	}

	@RequestMapping(value= "/student", method = RequestMethod.POST)
	public String save(@RequestBody Student jsonString) {
		logger.info("post debug:Student="+jsonString);
		if(jsonString.getId() == 0){
			//new student, add it
			studentService.addStudent(jsonString);
			return "Your request are submitted.";
		}
		return "can not set id.";
	}

	@RequestMapping(value= "/student/{studentId}", method = RequestMethod.PUT)
	public String update(@PathVariable() Integer studentId, @RequestBody Student jsonString) {
		Student s = studentService.getStudentById(studentId);
		if(jsonString.getName() == null ||
		jsonString.getDepartment() == null ||
		jsonString.getGrade() == -1 )
			return "failed, full student coloums required";
		s.setName(jsonString.getName());
		s.setDepartment(jsonString.getDepartment());
		s.setGrade(jsonString.getGrade());

		studentService.updateStudent(s);
		return "updated";
	}

	@RequestMapping(value= "/student/{studentId}", method = RequestMethod.PATCH)
	public String patch(@PathVariable() Integer studentId, @RequestBody Student jsonString) {
		Student s = studentService.getStudentById(studentId);
		if(jsonString.getName() != null) s.setName(jsonString.getName());
		if(jsonString.getDepartment() != null) s.setDepartment(jsonString.getDepartment());
		if(jsonString.getGrade() != 0) s.setGrade(jsonString.getGrade());
		studentService.updateStudent(s);
		return "updated";
	}

	@RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public String removeStudent(@PathVariable("id") int id){
		
        studentService.removeStudent(id);
        return "removed";
    }
	@ExceptionHandler(StudentNotFoundException.class)
	public String handleNotFound() {
	    return "Student not found";
	}

}
