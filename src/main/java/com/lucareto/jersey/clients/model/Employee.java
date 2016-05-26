package com.lucareto.jersey.clients.model;

import java.io.Serializable;

public class Employee implements Serializable, Node{

    private static final long serialVersionUID = -7319867924062833431L;
    public static final String NID = "person:";
    
    private String id;
    private String name;
    private String email;
    private String title;
    private String section;
    private Double salary;
    
    @Override
    public String getId() {
        return id;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public String getSection() {
        return section;
    }
    public void setSection(final String section) {
        this.section = section;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(final double salary) {
        this.salary = salary;
    }

}
