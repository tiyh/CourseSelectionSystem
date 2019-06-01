package com.example.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.StudentDAOImpl;
import com.example.exception.StudentNotFoundException;
import com.example.model.Student;
import com.example.model.UserRole;
import com.example.service.StudentService;

@RestController
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);
	private StudentService studentService;
	
    @Value("${jwt.header}")
    private String tokenHeader;
    
	@Autowired(required=true)
	public void setStudentService(StudentService ss){
		studentService = ss;
	}
    @RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
    String index(){
        return "Hello course selection!";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            String username,String password
    ) throws AuthenticationException{
        //  @RequestBody JwtAuthenticationRequest authenticationRequest
        final JSONObject studentJson = studentService.login(username,password);
        return ResponseEntity.ok(studentJson);
        //return ResponseEntity.ok(JSON.toJSONString(token));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = studentService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            logger.info("call /login return refreshedToken:"+refreshedToken);
            return ResponseEntity.ok(JSON.toJSONString(refreshedToken));
        }
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        studentService.logout(token);
        return ResponseEntity.ok(JSON.toJSONString(token));
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
	public Student register(@RequestBody Student jsonString) throws AuthenticationException{
		logger.info("post debug:Student="+jsonString);
		/*for(int i=0;i<500;i++) {
			Student student = new Student();
			student.setGrade(i);
			student.setUsername(jsonString.getUsername()+i);
			student.setDepartment(String.valueOf(i));
			student.setPassword("123456");
			student.setRoles(UserRole.USER);
			studentService.addStudent(student);
		}
		return jsonString;
		//build test database
		*/
		return studentService.addStudent(jsonString);
	}

	@RequestMapping(value= "/student/{studentId}", method = RequestMethod.PUT)
	public String update(@PathVariable() Integer studentId, @RequestBody Student jsonString) {
		Student s = studentService.getStudentById(studentId);
		if(jsonString.getUsername() == null ||
		jsonString.getDepartment() == null ||
		jsonString.getGrade() == -1 )
			return "failed, full student coloums required";
		s.setUsername(jsonString.getUsername());
		s.setPassword(jsonString.getPassword());
		s.setDepartment(jsonString.getDepartment());
		s.setGrade(jsonString.getGrade());

		studentService.updateStudent(s);
		return "updated";
	}

	@RequestMapping(value= "/student/{studentId}", method = RequestMethod.PATCH)
	public String patch(@PathVariable() Integer studentId, @RequestBody Student jsonString) {
		Student s = studentService.getStudentById(studentId);
		if(jsonString.getUsername() != null) s.setUsername(jsonString.getUsername());
		if(jsonString.getDepartment() != null) s.setDepartment(jsonString.getDepartment());
		if(jsonString.getGrade() != 0) s.setGrade(jsonString.getGrade());
		studentService.updateStudent(s);
		return "updated";
	}
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
