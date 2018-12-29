package com.cloudy.capter07;

import java.io.Serializable;

/**
 * @author cloudy
 * @createTime 2018/11/29
 * @description
 */
public class Leave implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 当前请假天数
     */
    private Integer day;

    /**
     * 总共请假天数
      */
    private Integer total = 0;

    public Leave(String name, Integer day) {
        this.name = name;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "name='" + name + '\'' +
                ", day=" + day +
                ", total=" + total +
                '}';
    }
}
