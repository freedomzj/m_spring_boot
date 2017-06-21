package com.domain;

/**
 * Created by zengjie on 17/6/20.
 */
public class User {

    private long id;

    private String username;

    private String password;

    private Department department;


    public  User(){

    }
    public User(long id,String username,String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
