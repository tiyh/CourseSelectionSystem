package com.example.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "selectionsystem",type = "searchcourse",shards = 1, replicas = 0)
public class SearchCourse {
    @Id
    private int id;
    
    private String courseName = "";
    private int capacity = 0;
    private int orderedNum = 0;

    public int getId() {
        return id;
    }

    public void setId(@NotNull int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(@NotNull String name) {
        this.courseName = name;
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

        SearchCourse s = (SearchCourse) obj;

        return s.getId()==this.id &&
                s.getCourseName().equals(this.courseName);
    }

    @Override
    public String toString() {
        return "{\"id\":" + this.id +
                ",\"name\": " + this.courseName +
                ",\"capacity\": " + this.capacity +
                ",\"orderedNum\": " + this.orderedNum +
                "}";
    }
}
