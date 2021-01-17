/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

import java.util.Date;

/**
 *
 * @author User
 */
public class EmployeeClass {
    
    private int eid;
    private String ename;
    private String job;
    private int mgr;
    private int edeptno;
    private int sal;
    private int comm;
    private Date hdate;

    EmployeeClass(int aInt, String string, String string0, int aInt0, int aInt1, int aInt2, int aInt3, java.sql.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getEid() {
        return eid;
    }

    public String getEname() {
        return ename;
    }

    public String getJob() {
        return job;
    }

    public int getMgr() {
        return mgr;
    }

    public int getEdeptno() {
        return edeptno;
    }

    public int getSal() {
        return sal;
    }

    public int getComm() {
        return comm;
    }

    public Date getHdate() {
        return hdate;
    }

    public EmployeeClass(int eid, String ename, String job, int mgr, int edeptno, int sal, int comm, Date hdate) {
        this.eid = eid;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.edeptno = edeptno;
        this.sal = sal;
        this.comm = comm;
        this.hdate = hdate;
    }
        
}
