package com.example.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Table(name = "student", catalog = "")
public class Student implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2958716126255051178L;

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "grade", nullable = false)
    private int grade = -1;

    @Column(name = "password", nullable = false)
    private String password;

    //@ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Column(name = "userrole")
    private String roles;

    private Date lastPasswordResetDate;

    public int getId() {
        return id;
    }

    public void setId(@NotNull int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String name) {
        this.username = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(@NotNull String department) {
        this.department = department;
    }

    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }
    public String getRoles() {
        return roles;
    }

    public void setRoles(UserRole roles) {
        this.roles = roles.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }

        Student s = (Student) obj;

        return s.getId()==this.id &&
                s.getUsername().equals(this.username) &&
                s.getDepartment().equals(this.department);
    }
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Override
    public String toString() {
        return "{ \"id\": " + this.id +
                ", \"name\":\"" + this.username +
                "\",\"department\": \"" + this.department +
                ", grade: " + this.grade +
                " }";
    }
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

	@Override
	@JsonIgnore
	public ArrayList<SimpleGrantedAuthority> getAuthorities() {
		ArrayList<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
		String roles = this.getRoles();
        auths.add(new SimpleGrantedAuthority("ROLE_"+roles));
        return auths;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

}