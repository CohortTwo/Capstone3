/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emprecordmanagement;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 *
 * @author sridh
 */
public class Employees {
    
    private int eid;
    private String ename;
    private String job;
    private int mgr;
    private int edeptno;
    private int sal;
    private int comm;
    private String hdate;
   

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
    private Button delete;

    public String getMgrname() {
        return mgrname;
    }

    public void setMgrname(String mgrname) {
        this.mgrname = mgrname;
    }
    private Button edit;
    private CheckBox cb;
    private String dept;
    private String mgrname;

    public CheckBox getCb() {
        return cb;
    }

    public void setCb(CheckBox cb) {
        this.cb = cb;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }


    public Employees(int eid, String ename, String job, int mgr, int edeptno, int sal, int comm, String hdate) {
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

    public String getHdate() {
        return hdate;
    }

    public void setHdate(String hdate) {
        this.hdate = hdate;
    }

    @Override
    public String toString() {
        return "Employees{" + "eid=" + eid + ", ename=" + ename + ", job=" + job + ", mgr=" + mgr + ", edeptno=" + edeptno + ", sal=" + sal + ", comm=" + comm + ", hdate=" + hdate + ", delete=" + delete + ", edit=" + edit + '}';
    }
    
}
