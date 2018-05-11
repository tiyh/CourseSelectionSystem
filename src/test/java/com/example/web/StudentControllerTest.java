package com.example.web;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.model.Student;
import com.example.service.StudentService;
import com.example.web.StudentController;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private StudentService studentService;
    @Test
    public void testHome() throws Exception {
        this.mvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().string("Hello course selection!"));
    }
    
    @Test
    public void testSave() throws Exception {
    	Student student = new Student();
        student.setUsername("test"+111);
        student.setDepartment("test"+111);
        student.setGrade(1);
        String requestJson = JSONObject.toJSONString(student);
        this.mvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk()).andExpect(content().string("Your request are submitted."));
    }
    
    @Test
    public void testListStudents() throws Exception {
    	List<Student> returnList = new ArrayList<Student>(); 
		for(int i=0;i<10; i++){
			Student student = new Student();
            student.setId(i);
            student.setUsername("test"+i);
            student.setDepartment("test"+i);
            student.setGrade(i);
            returnList.add(student);
        }
        given(this.studentService.listStudents())
                .willReturn(returnList);
        this.mvc.perform(get("/students")
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetStudentById() throws Exception {
			Student student = new Student();
            student.setId(1);
            student.setUsername("test"+1);
            student.setDepartment("test"+1);
            student.setGrade(1);
        given(this.studentService.getStudentById(1))
                .willReturn(student);
        this.mvc.perform(get("/student/1")
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
