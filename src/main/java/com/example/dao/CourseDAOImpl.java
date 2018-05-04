package com.example.dao;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Course;

@Repository
public class CourseDAOImpl implements CourseDAO {
	private static final Logger logger = LoggerFactory.getLogger(CourseDAOImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void addCourse(Course p) {
		entityManager.persist(p);
		logger.info("Course saved successfully, Course Details="+p);
	}
	@Transactional
	@Override
	public void updateCourse(Course p) {
		Course course = entityManager.find(Course.class, p.getId());
		entityManager.merge(course);
		logger.info("Course updated successfully, Course Details="+course);
	}
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Course> listCourses() {
		
		Query query = (Query) entityManager.createQuery("select course from Course course");
		List<Course> returnList = query.getResultList();
		/*
		List<Course> returnList = new ArrayList<Course>(); 
		Query query = (Query) entityManager.createNativeQuery("select * from course");
		List<Object> coursesList = query.getResultList();
		for(int i=0;i<coursesList.size(); i++){  
            Course course = new Course();
            Object[] objectArray = (Object[]) coursesList.get(i);  
            course.setId((Integer)objectArray[0]);  
            course.setName((String)objectArray[1]);  
            course.setCapacity((Integer)objectArray[2]);
            course.setOrderedNum((Integer)objectArray[3]);
            returnList.add(course);
        }  */
		for(Course p : returnList){
			logger.info("Course List::"+p);
		}
		return returnList;
	}
	@Transactional
	@Override
	public Course getCourseById(int id) {
		Course course = entityManager.find(Course.class, id);
		logger.info("Course loaded successfully, Course details="+course);
		return course;
	}
	@Transactional
	@Override
	public void removeCourse(int id) {
		Course course = entityManager.find(Course.class, id);
		if(null != course){
			entityManager.remove(course);
			logger.info("Course deleted successfully, course details="+course);
		}
	}
}
