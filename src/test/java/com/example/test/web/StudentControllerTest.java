package com.example.test.web;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.CourseSelectionSystem;
import com.example.model.Student;
import com.example.model.UserRole;
import com.example.service.StudentService;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter; 
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;  

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CourseSelectionSystem.class)
public class StudentControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(StudentControllerTest.class);
	
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;
    private MockMvc mvc;
    @MockBean
    private StudentService studentService;
    private static final String USERNAME ="tiyunheng";
    private static final String PASSWORD ="123456";
    @Before  
    public void setup() throws Exception {
        //initData();  
    	mvc = MockMvcBuilders.webAppContextSetup(context)  
                .defaultRequest(get("/login").with(user(USERNAME).password(PASSWORD)  
                        .authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))) 
                .addFilters(springSecurityFilterChain)
                .build();
    }
    
    
    @Test
    public void testHome() throws Exception {
        this.mvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().string("Hello course selection!"));
    }
    
    @Test
    //com.fasterxml.jackson.databind.JsonMappingException: A granted authority textual representation is required
    public void testSave()throws Exception{
    	/*with(authentication(SecurityContextHolder.getContext().getAuthentication())).*/
    	Student student = new Student();
        student.setUsername("test");
        student.setPassword("123456");
        student.setDepartment("test");
        student.setGrade(1);
        student.setRoles(UserRole.USER);
        student.setLastPasswordResetDate(new Date());
        //given(this.studentService.addStudent(student))
        //.willReturn(student);
        String requestJson = JSONObject.toJSONString(student);
        logger.info("requestJson:"+requestJson);
        this.mvc.perform(post("/student",student).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                        .andExpect(status().isOk());
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
            student.setRoles(UserRole.ADMIN);
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
            student.setRoles(UserRole.ADMIN);
        given(this.studentService.getStudentById(1))
                .willReturn(student);
        this.mvc.perform(get("/student/1")
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}