package com.enndfp.pojo;

/**
 * @author Enndfp
 * @date 2023/3/18
 */
public class Course {
    private Integer classId;
    private String className;
    private String classBegin;
    private String classTime;
    private String classCoach;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassBegin() {
        return classBegin;
    }

    public void setClassBegin(String classBegin) {
        this.classBegin = classBegin;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getClassCoach() {
        return classCoach;
    }

    public void setClassCoach(String classCoach) {
        this.classCoach = classCoach;
    }
}
