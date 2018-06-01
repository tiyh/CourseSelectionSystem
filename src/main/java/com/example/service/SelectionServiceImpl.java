package com.example.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.example.model.Course;
import com.example.dao.SelectionDAO;
import org.springframework.transaction.annotation.Transactional;
@Service
public class SelectionServiceImpl implements SelectionService{
	private SelectionDAO SelectionDAO;
	private static final Logger logger = LoggerFactory.getLogger(SelectionServiceImpl.class);
    @Autowired
	public void setSelectionDAO(SelectionDAO SelectionDAO) {
		this.SelectionDAO = SelectionDAO;
	}
    @Transactional
	@Override
	@Caching(cacheable=@Cacheable(value = "selectioncache", keyGenerator = "wiselyKeyGenerator"),evict ={@CacheEvict(value = "orderablecache", allEntries = true),@CacheEvict(value = "selectionlistcache", allEntries = true) })
	public /*synchronized
	*AOP Transactional make synchronized not work
	*/ boolean selectCourse(int studentId, int courseId) {
		if(courseOrderable(courseId,studentId)){
			logger.info("Student:"+ studentId +" has selected courseId:"+courseId);
			return this.SelectionDAO.selectCourse(studentId, courseId);
		}
		else{
			return false;
		}
	}
    @Transactional
	@Override
	@Caching(evict = { @CacheEvict(value = "selectioncache", allEntries = true),@CacheEvict(value = "orderablecache", allEntries = true),@CacheEvict(value = "selectionlistcache", allEntries = true) })
	public void deleteSelectedCourse(int selectionId) {
		this.SelectionDAO.deleteSelectedCourse(selectionId);
	}

	@Override
	@Cacheable(value = "orderablecache", keyGenerator = "wiselyKeyGenerator")
	public boolean courseOrderable(int courseId,int studentId){
		return this.SelectionDAO.courseOrderable(courseId,studentId);
	}
    @Transactional
	@Override
	@Caching(cacheable=@Cacheable(value = "selectionlistcache", keyGenerator = "wiselyKeyGenerator"))
	public List<Course> getOrderedCourse(int studentId) {
		return this.SelectionDAO.getOrderedCourse(studentId) ;
	}
	

}
