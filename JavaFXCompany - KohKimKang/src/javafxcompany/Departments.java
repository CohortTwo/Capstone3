
package javafxcompany;

public class Departments {
    private int deptId;
    private String deptName;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Departments(int deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }
    
    
}
