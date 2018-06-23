package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.Course;
import com.example.model.SearchCourse;
import com.example.service.CourseSearchService;

@RestController
public class SearchController {

    private CourseSearchService courseSearchService;
    @Autowired
	public void setCourseSearchService(CourseSearchService cs){
    	this.courseSearchService = cs;
	}
    @RequestMapping(value = "/search/all", method = RequestMethod.GET)
    public List<SearchCourse> index1(
                       @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                       @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize) {
        Pageable pageable = new PageRequest(pageIndex,pageSize);
        Page<SearchCourse> courses = courseSearchService.findAll(pageable);
        List<SearchCourse> list = courses.getContent();
        return list;
    }

    @RequestMapping(value = "/search/course", method = RequestMethod.GET)
    public List<SearchCourse> index2(@RequestParam(value="name",required=false,defaultValue="math") String name,
                         @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                         @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize) {
        Pageable pageable = new PageRequest(pageIndex,pageSize);
        Page<SearchCourse> courses = courseSearchService.findByName(name,pageable);
        List<SearchCourse> list = courses.getContent();
        return list;
    }
}
