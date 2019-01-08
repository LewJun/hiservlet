package com.microandroid.bean;

import java.io.Serializable;

public class Emp implements Serializable {

    public Integer empno;

    public String ename;

    public String job;

    public Emp() {
    }

    public Emp(Integer empno, String ename, String job) {
        this();
        this.empno = empno;
        this.ename = ename;
        this.job = job;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
