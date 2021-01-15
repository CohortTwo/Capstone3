package emprecordmanagement;

import java.util.Date;



public class Employees {
    
    private int eid;
    private String ename;
    private String job;
    private int mgr;
    private int edeptno;
    private int sal;
    private int comm;
    private Date hdate;

    public Employees(int eid, String ename, String job, int mgr, int edeptno, int sal, int comm, Date hdate) {
        this.eid = eid;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.edeptno = edeptno;
        this.sal = sal;
        this.comm = comm;
        this.hdate = hdate;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public int getEdeptno() {
        return edeptno;
    }

    public void setEdeptno(int edeptno) {
        this.edeptno = edeptno;
    }

    public int getSal() {
        return sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    public int getComm() {
        return comm;
    }

    public void setComm(int comm) {
        this.comm = comm;
    }

    public Date getHdate() {
        return hdate;
    }

    public void setHdate(Date hdate) {
        this.hdate = hdate;
    }
    
}