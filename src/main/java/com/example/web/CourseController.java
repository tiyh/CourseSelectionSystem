package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Course;
import com.example.service.CourseService;

@RestController
public class CourseController {
	private CourseService CourseService;
	@Autowired(required=true)
	public void setCourseService(CourseService cs){
		CourseService = cs;
	}
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public List<?> getCoursesInJSON() {

		return CourseService.listCourses();
	}

	@RequestMapping(value = "/course/{CourseId}", method = RequestMethod.GET)
	public Course getCourseById(@PathVariable() Integer CourseId) {
		return CourseService.getCourseById(CourseId);
	}

	@RequestMapping(value= "/course", method = RequestMethod.POST)
	public String save(@RequestBody Course jsonString) {

		if(jsonString.getId() == 0){
			//new Course, add it
			CourseService.addCourse(jsonString);
		}
		return "Your request are submitted.";
	}

	@RequestMapping(value= "/course/{CourseId}", method = RequestMethod.PUT)
	public String update(@PathVariable() Integer CourseId, @RequestBody Course jsonString) {
		Course s = CourseService.getCourseById(CourseId);
		if(jsonString.getName() == null ||
		jsonString.getCapacity() == -1 ||
		jsonString.getOrderedNum() == -1 )
			return "failed, full Course coloums required";

		s.setName(jsonString.getName());
		s.setCapacity(jsonString.getCapacity());
		s.setOrderedNum(jsonString.getOrderedNum());

		CourseService.updateCourse(s);
		return "updated";
	}

	@RequestMapping(value= "/course/{CourseId}", method = RequestMethod.PATCH)
	public String patch(@PathVariable() Integer CourseId, @RequestBody Course jsonString) {
		Course s = CourseService.getCourseById(CourseId);
		if(jsonString.getName() != null) s.setName(jsonString.getName());
		if(jsonString.getCapacity() != 0) s.setCapacity(jsonString.getCapacity());
		if(jsonString.getOrderedNum() != 0) s.setOrderedNum(jsonString.getOrderedNum());
		CourseService.updateCourse(s);
		return "updated";
	}

	@RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
    public String removeCourse(@PathVariable("id") int id){
        CourseService.removeCourse(id);
        return "removed";
    }

}
