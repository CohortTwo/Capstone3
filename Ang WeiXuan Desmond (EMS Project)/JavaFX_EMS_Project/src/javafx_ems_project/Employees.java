/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_ems_project;

import java.sql.Date;

/**
 *
 * @author Desmond
 */
public class Employees {

    private int empID;
    private String fname;
    private String lname;
    private String email;
    private String phoneNum;
    private Date hdate;
    private String jobID;
    private int salary;
    private double commPCT;
    private int managerID;
    private int deptID;

    public Employees(int empID, String fname, String lname, String email, String phoneNum, Date hdate, String jobID, int salary, double commPCT, int managerID, int deptID) {
        this.empID = empID;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phoneNum = phoneNum;
        this.hdate = hdate;
        this.jobID = jobID;
        this.salary = salary;
        this.commPCT = commPCT;
        this.managerID = managerID;
        this.deptID = deptID;
    }

    public int getEmpID() {
        return empID;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public Date getHdate() {
        return hdate;
    }

    public String getJobID() {
        return jobID;
    }

    public int getSalary() {
        return salary;
    }

    public double getCommPCT() {
        return commPCT;
    }

    public int getManagerID() {
        return managerID;
    }

    public int getDeptID() {
        return deptID;
    }

    @Override
    public String toString() {
        return "Employees{" + "empID=" + empID + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", phoneNum=" + phoneNum + ", hdate=" + hdate + ", jobID=" + jobID + ", salary=" + salary + ", commPCT=" + commPCT + ", managerID=" + managerID + ", deptID=" + deptID + '}';
    }

    

}
