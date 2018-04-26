package com.model;

//import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Created by 340066 on 2017/12/21.
 */
public class UserEo {
    private Long id;
    private String userName;
    private String password;
    private Integer gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public UserEo(Long id) {
        this.id = id;
    }

    public UserEo() {
    }
}
