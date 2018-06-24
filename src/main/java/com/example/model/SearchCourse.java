package com.example.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "selectionsystem",type = "searchcourse",shards = 1, replicas = 0)
public class SearchCourse {
    @Id
    private int id;
    
    private String subject = "";
    private int capacity = 0;
    private int ordered = 0;

    public int getId() {
        return id;
    }

    public void setId(@NotNull int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(@NotNull String name) {
        this.subject = name;
    }

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getOrdered() { return ordered; }

    public void setOrderedNum(int orderedNum) { this.ordered = orderedNum; }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }

        SearchCourse s = (SearchCourse) obj;

        return s.getId()==this.id &&
                s.getSubject().equals(this.subject);
    }

    @Override
    public String toString() {
        return "{\"id\":" + this.id +
                ",\"subject\": " + this.subject +
                ",\"capacity\": " + this.capacity +
                ",\"ordered\": " + this.ordered +
                "}";
    }
}
