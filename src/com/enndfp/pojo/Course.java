package com.enndfp.pojo;

/**
 * @author Enndfp
 * @date 2023/3/18
 */
public class Course {
    /**
     * id
     */
    private Integer classId;
    /**
     * 课程名
     */
    private String className;
    /**
     * 课程开始时间
     */
    private String classBegin;
    /**
     * 课程时长
     */
    private String classTime;
    /**
     * 课程教练
     */
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
