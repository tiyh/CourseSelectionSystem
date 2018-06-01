package com.example.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Course;
import com.example.service.SelectionService;
@RestController
public class SelectionController {
	private SelectionService selectionService;
	private static final Logger logger = LoggerFactory.getLogger(SelectionController.class);
	
	@Autowired(required=true)
	public void setSelectionService(SelectionService ss){
		selectionService = ss;
	}
	@RequestMapping(value = "/students/{StudentId}/selections", method = RequestMethod.GET)
	public List<Course> getOrderedCourse(@PathVariable() int StudentId) {
		logger.info("getOrderedCourse:StudentId="+StudentId);
		return selectionService.getOrderedCourse(StudentId);
	}
	
	@RequestMapping(value = "/students/{StudentId}/selections/{CourseId}", method = RequestMethod.GET)
	public  synchronized boolean selectCourse(@PathVariable int StudentId,@PathVariable int CourseId) {
		return selectionService.selectCourse(StudentId,CourseId);
	}
	@RequestMapping(value = "/students/selections/{selectionId}", method = RequestMethod.DELETE)
	public boolean deleteCourse(@PathVariable() int selectionId) {
		selectionService.deleteSelectedCourse(selectionId);
		return true;
	}
}
