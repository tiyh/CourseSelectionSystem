package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "course", catalog = "")
public class Course {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;
    @Column(name = "name", nullable = false)
    private String name = "";
    @Column(name = "capacity", nullable = false)
    private int capacity = 0;
    @Column(name = "orderednum", nullable = false)
    private int orderedNum = 0;

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

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getOrderedNum() { return orderedNum; }

    public void setOrderedNum(int orderedNum) { this.orderedNum = orderedNum; }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }

        Student s = (Student) obj;

        return s.getId()==this.id &&
                s.getName().equals(this.name);
    }

    @Override
    public String toString() {
        return "{\"id\":" + this.id +
                ",\"name\": " + this.name +
                ",\"capacity\": " + this.capacity +
                ",\"orderedNum\": " + this.orderedNum +
                "}";
    }
}
