/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emprecordmanagement;

import java.util.ArrayList;

/**
 *
 * @author LaiYing
 */
public class Depts {
    private int deptno;
    private String dname;
    private String loc;

    public Depts(int deptno, String dname, String loc) {
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Depts{" + "deptno=" + deptno + ", dname=" + dname + ", loc=" + loc + '}';
    }

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

//    public int findDeptNoByDname(ArrayList<Depts> depts, String dname) {
//        int i = 0;
//        for (Depts dept : depts) {
//            System.out.println(depts);
//            if (dept.getDname() == null ? dname == null : dept.getDname().equals(dname)) {
//                i = dept.getDeptno();
//            }
//        }
//        return i;
//    }
//    public String findDnameByDeptNo(ArrayList<Depts> depts, int deptno){
//        String name = "";
//        for (Depts dept : depts) {
//            System.out.println(depts);
//            if(dept.getDeptno()== deptno){
//                name = dept.getDname();
//            }
//        }
//        return name;
//    }
}
