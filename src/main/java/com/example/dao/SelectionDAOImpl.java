package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.model.Selection;
import com.example.model.Student;
import com.example.model.Course;

@Repository
public class SelectionDAOImpl implements SelectionDAO {
	private static final Logger logger = LoggerFactory.getLogger(CourseDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public boolean selectCourse(int studentId, int courseId) {
        Course course =entityManager.find(Course.class,courseId);
        if(course==null) return false;
        int orderedNum = course.getOrderedNum();
        orderedNum = orderedNum + 1;
        course.setOrderedNum(orderedNum);
        entityManager.merge(course); 
        Student student = entityManager.find(Student.class,studentId);
        if(student==null) return false;
        Selection selection = new Selection();
        try {
        selection.setStudentNum(student.getId());
        selection.setCourseNum(course.getId());
        entityManager.persist(selection);
        entityManager.persist(course);
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
        return true;
	}
	
	@Override
	public void deleteSelectedCourse(int selectionId) {
		Selection selection = entityManager.find(Selection.class, selectionId);
		if(null != selection){
	        Course course =entityManager.find(Course.class,selection.getCourseNum());
	        course.setOrderedNum(course.getOrderedNum()-1);
			entityManager.remove(selection);
			entityManager.persist(course);
			logger.info("Student deleted successfully, student details="+selection);
		}
		
	}
	@Override
	public List<Course> getOrderedCourse(int studentId) {
		    List<Course> studentCourseList = new ArrayList<Course>();
	        String hql = "select course from Selection selection where selection.student =:studentId";
	        Query query = (Query) entityManager.createQuery(hql);
	        query.setParameter("studentId", studentId);
	        studentCourseList = query.getResultList();
	        return studentCourseList;
	}
	@Override
	public boolean courseOrderable(int courseId,int studentId){
		Course course =entityManager.find(Course.class,courseId);
		if(course==null) return false;
        if(course.getOrderedNum()<course.getCapacity()){
    		String hql = "select id from Selection selection where selection.course =:courseId and selection.student =:studentId";
            Query query = (Query) entityManager.createQuery(hql);
            query.setParameter("courseId", courseId);
            query.setParameter("studentId", studentId);
            List<Integer> list = new ArrayList<Integer>();
            list =  query.getResultList();
            if(list.isEmpty()) return true;
            else return false;
        }else return false;
	}


}
