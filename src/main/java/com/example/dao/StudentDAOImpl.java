package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.model.Student;

@Repository
public class StudentDAOImpl implements StudentDAO{
	private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Student addStudent(Student p) {
		entityManager.persist(p);
		logger.info("Student saved successfully, Student Details="+p);
		return p;
	}

	@Override
	public void updateStudent(Student p) {
		Student student = entityManager.find(Student.class, p.getId());
		entityManager.merge(student);
		logger.info("Student updated successfully, Student Details="+p);
	}

	@Override
	public List<Student> listStudents() {
		Query query = (Query) entityManager.createQuery("select student from Student student");
		ArrayList<Student> returnList = (ArrayList<Student>) query.getResultList();
		/*
		List<Student> returnList = new ArrayList<Student>(); 
		Query query = (Query) entityManager.createNativeQuery("select * from student");
		List<Object> studentsList = query.getResultList();
		for(int i=0;i<studentsList.size(); i++){  
			Student student = new Student();
            Object[] objectArray = (Object[]) studentsList.get(i);  
            student.setId((Integer)objectArray[0]);  
            student.setName((String)objectArray[1]);  
            student.setDepartment((String)objectArray[2]);
            student.setGrade((Integer)objectArray[3]);//setOrderedNum((Integer)objectArray[3]);
            returnList.add(student);
        }*/  
		for(Student p : returnList){
			logger.info("Student List::"+p);
		}
		return returnList;
	}

	@Override
	public Student getStudentById(int id) {
		
		Student p = entityManager.find(Student.class, id);
		logger.info("Student loaded successfully, Student details="+p);
		return p;
	}

	@Override
	public Student getStudentByUsername(String name){
        String hql = "select id from Student student where student.username =:name";
        Query query = (Query) entityManager.createQuery(hql);
        query.setParameter("name", name);
        List id = query.getResultList();
        if (id.size()!=0) {
		Student p = entityManager.find(Student.class, id.get(0));
		logger.info("Student loaded successfully, Student details="+p);
		return p;
		}else return null;
	}
	@Override
	public void removeStudent(int id) {
		Student student = entityManager.find(Student.class, id);
		if(null != student){
			entityManager.remove(student);
			logger.info("Student deleted successfully, student details="+student);
		}
		logger.info("Student deleted successfully, student details="+student);
	}

}
