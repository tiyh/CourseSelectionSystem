package com.example.web;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.model.Course;
import com.example.service.CourseService;
import com.example.web.CourseController;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


    @RunWith(SpringRunner.class)
    @WebMvcTest(CourseController.class)
    public class CourseControllerTest {
        @Autowired
        private MockMvc mvc;
        @MockBean
        private CourseService courseService;

        @Test
        public void testSave() throws Exception {
            Course course = new Course();
            course.setId(111);
            course.setName("test"+111);
            course.setCapacity(100);
            course.setOrderedNum(0);
            String requestJson = JSONObject.toJSONString(course);
            this.mvc.perform(post("/course",course).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                    .andExpect(status().isOk()).andExpect(content().string("Your request are submitted."));
        }

        @Test
        public void testListCourses() throws Exception {
            List<Course> returnList = new ArrayList<Course>();
            for(int i=0;i<10; i++){
                Course course = new Course();
                course.setId(i);
                course.setName("test"+i);
                course.setCapacity(100);
                course.setOrderedNum(i);
                returnList.add(course);
            }
            given(this.courseService.listCourses())
                    .willReturn(returnList);
            this.mvc.perform(get("/courses")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        @Test
        public void testGetCourseById() throws Exception {
            Course course = new Course();
            course.setId(111);
            course.setName("test"+111);
            course.setCapacity(100);
            course.setOrderedNum(0);
            given(this.courseService.getCourseById(1))
                    .willReturn(course);
            this.mvc.perform(get("/course/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

