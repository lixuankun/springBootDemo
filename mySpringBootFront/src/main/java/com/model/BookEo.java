package com.model;


/**
 * Created by lxk on 2018/4/3.
 */
public class BookEo {
    private Long id;
    private Long uid;
    private Integer bookType;
    private String bookName;
    private Integer number;
    private Boolean haveOnline;

    public Boolean getHaveOnline() {
        return haveOnline;
    }

    public void setHaveOnline(Boolean haveOnline) {
        this.haveOnline = haveOnline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getBookType() {
        return bookType;
    }

    public void setBookType(Integer bookType) {
        this.bookType = bookType;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BookEo(Long id) {
        this.id = id;
    }

    public BookEo() {
    }
}
