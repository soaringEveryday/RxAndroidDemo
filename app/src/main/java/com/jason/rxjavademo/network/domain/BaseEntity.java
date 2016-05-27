package com.jason.rxjavademo.network.domain;

import java.util.List;

/**
 * Created by Chen Haitao on 2016/5/27.
 */
public class BaseEntity {

    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects : []
     * title : 豆瓣电影Top250
     */

    private int count;
    private int start;
    private int total;
    private String title;
    private List<MovieEntity> subjects;

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

    public List<MovieEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<MovieEntity> subjects) {
        this.subjects = subjects;
    }
}
