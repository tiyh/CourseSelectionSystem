package com.example.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.StudentDAO;
import com.example.model.Student;
import com.example.model.UserRole;
import com.example.util.JwtTokenUtil;

@Service
public class StudentServiceImpl implements StudentService{
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	private StudentDAO studentDAO;
	@Autowired
	private CustomUserService customUserService;
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Autowired
	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}
    @Transactional
	@Override
	@Caching(cacheable=@Cacheable(value = "studentcache", keyGenerator = "wiselyKeyGenerator"),evict = @CacheEvict(value = "liststudentcache", allEntries = true))
	public Student addStudent(Student p) {
		final String username = p.getUsername();
		if(customUserService.loadUserByUsername(username)!=null) {
            return null;
        }
		if(p.getId() != 0){
			return null;
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = p.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        logger.info("encodedPassword length:"+encodedPassword.length());
        p.setPassword(encodedPassword);
        p.setLastPasswordResetDate(new Date());
		//new student, add it
		p.setRoles(UserRole.USER);
		return this.studentDAO.addStudent(p);
	}
    @Transactional
	@Override
	@Caching(put = @CachePut("studentcache"), evict = @CacheEvict(value = "liststudentcache", allEntries = true))
	public void updateStudent(Student p) {
		this.studentDAO.updateStudent(p);
	}
    @Transactional
	@Override
	@Cacheable(value = "liststudentcache",keyGenerator = "wiselyKeyGenerator")
	public List<Student> listStudents() {
		return this.studentDAO.listStudents();
	}
    @Transactional
	@Override
	@Cacheable(value = "studentcache", keyGenerator = "wiselyKeyGenerator")
	public Student getStudentById(int id) {
		return this.studentDAO.getStudentById(id);
	}
    @Transactional
	@Override
	@Caching(evict = { @CacheEvict("studentcache"),@CacheEvict(value = "liststudentcache", allEntries = true) })
	public void removeStudent(int id) {
		this.studentDAO.removeStudent(id);
	}

    @Override
    public String refresh(String oldToken) {
        if(oldToken==null) return null;
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Student user = (Student) customUserService.loadUserByUsername(username);
        if(user==null) return null;
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public JSONObject login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final Student student = (Student) customUserService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(student);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id",student.getId());
        jsonObject.put("token", token);
        return jsonObject;
    }

}
