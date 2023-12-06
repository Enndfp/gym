package com.enndfp.pojo;

/**
 * @author Enndfp
 * @date 2023/3/17
 */
public class Employee {
    /**
     * 员工号
     */
    private Integer employeeAccount;
    /**
     * 姓名
     */
    private String employeeName;
    /**
     * 性别
     */
    private String employeeGender;
    /**
     * 年龄
     */
    private Integer employeeAge;
    /**
     * 入职时间
     */
    private String entryTime;
    /**
     * 职务
     */
    private String staff;
    /**
     * 备注信息
     */
    private String employeeMessage;

    public Integer getEmployeeAccount() {
        return employeeAccount;
    }

    public void setEmployeeAccount(Integer employeeAccount) {
        this.employeeAccount = employeeAccount;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public Integer getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(Integer employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getEmployeeMessage() {
        return employeeMessage;
    }

    public void setEmployeeMessage(String employeeMessage) {
        this.employeeMessage = employeeMessage;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeAccount=" + employeeAccount +
                ", employeeName='" + employeeName + '\'' +
                ", employeeGender='" + employeeGender + '\'' +
                ", employeeAge=" + employeeAge +
                ", entryTime='" + entryTime + '\'' +
                ", staff='" + staff + '\'' +
                ", employeeMessage='" + employeeMessage + '\'' +
                '}';
    }
}
