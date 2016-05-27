package com.jason.rxjavademo.network.domain;

/**
 * Created by Chen Haitao on 2016/5/27.
 */
public class BaseEntity<T> {

    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects : []
     * title : 豆瓣电影Top250
     */

    private int resultCode;
    private String resultMessage;
    private int count;
    private int start;
    private int total;
    private String title;
    private T subjects;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }
}
