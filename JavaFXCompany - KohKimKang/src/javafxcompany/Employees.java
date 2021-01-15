package javafxcompany;

import java.sql.Date;

public class Employees {

    private int empId;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String mgrName;
    private String deptName;
    private int sal;
    private double comm;
    private Date hdate;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getSal() {
        return sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    public double getComm() {
        return comm;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    public Date getHdate() {
        return hdate;
    }

    public void setHdate(Date hdate) {
        this.hdate = hdate;
    }

    
    public Employees(int empId, String firstName, String lastName, String mgrName, String jobTitle, int sal, double comm, String deptName, Date hdate) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mgrName = mgrName;
        this.jobTitle = jobTitle;
        this.sal = sal;
        this.comm = comm;
        this.deptName = deptName;
        this.hdate = hdate;
    }

}
