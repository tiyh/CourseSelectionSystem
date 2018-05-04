package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "student", catalog = "")
public class Student {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;
    @Column(name = "name", nullable = false)
    private String name = "";
    @Column(name = "department", nullable = false)
    private String department = "";
    @Column(name = "grade", nullable = false)
    private int grade = -1;

    public int getId() {
        return id;
    }

    public void setId(@NotNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(@NotNull String department) {
        this.department = department;
    }

    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }

        Student s = (Student) obj;

        return s.getId()==this.id &&
                s.getName().equals(this.name) &&
                s.getDepartment().equals(this.department);
    }

    @Override
    public String toString() {
        return "{ \"id\": " + this.id +
                ", \"name\":\"" + this.name +
                "\",\"department\": \"" + this.department +
                ", grade: " + this.grade +
                " }";
    }
}
