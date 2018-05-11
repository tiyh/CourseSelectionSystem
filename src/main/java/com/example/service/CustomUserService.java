package com.example.service;

import com.example.dao.StudentDAO;
import com.example.model.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    StudentDAO userDao;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserService.class);
    public UserDetails loadUserByUsername(String userName) {
    	logger.info("call loadUserByUsername,userName: " + userName);
    	if(userName==null) return null;
        Student user = userDao.getStudentByUsername(userName);
        if (user != null) {
            String userRole = user.getRoles();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            //for (UserRole userRole : userRoles) {
                if (userRole != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+userRole);
                    grantedAuthorities.add(grantedAuthority);
                }
            //}
            //user.setGrantedAuthorities(grantedAuthorities);
            return user;
        } else {
        	logger.info("userName: " + userName + " do not exist!");
        	return null;
        }
    }
}