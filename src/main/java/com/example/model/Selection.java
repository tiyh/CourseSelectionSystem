package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "selection", catalog = "")
public class Selection {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int selectionId;
    @Column(name = "student")
    private int student;
    @Column(name = "course")
    private int course;


    public int getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }


    public int getCourseNum() {
        return course;
    }

    public int setCourseNum(int course) {
        return this.course = course;
    }


    public int getStudentNum() {
        return student;
    }

    public void setStudentNum(int studentNum) {
        this.student = studentNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Selection that = (Selection) o;

        if (selectionId!=that.selectionId) return false;

        return true;
    }
    @Override
    public String toString() {
        return "{\"selectionId\":" + this.selectionId +
                ",\"studentNum\":" + this.student +
                ",\"courseNum\":" + this.course +
                " }";
    }

}
