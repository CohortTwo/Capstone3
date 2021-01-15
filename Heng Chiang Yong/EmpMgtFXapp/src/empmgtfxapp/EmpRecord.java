/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empmgtfxapp;

/**
 *
 * @author chiangyong
 */
public class EmpRecord {
    
    private int eID;
    private String efirstName;
    private String elastName;
    private String eEmail;
    private String ePhone;
    private String eHireDate;
    private String eJobID;
    private float eSalary;
    private float eCommPct;
    private int eMgrID;
    private int eDepID;

    public EmpRecord(int eID, String efirstName, String elastName, String eEmail, 
            String ePhone, String eHireDate, String eJobID, float eSalary, 
            float eCommPct, int eMgrID, int eDepID) {
        this.eID = eID;
        this.efirstName = efirstName;
        this.elastName = elastName;
        this.eEmail = eEmail;
        this.ePhone = ePhone;
        this.eHireDate = eHireDate;
        this.eJobID = eJobID;
        this.eSalary = eSalary;
        this.eCommPct = eCommPct;
        this.eMgrID = eMgrID;
        this.eDepID = eDepID;
    }

    public EmpRecord(int eMgrID) {
        this.eMgrID = eMgrID;
    }

    
    public int getEID() {
        return eID;
    }

    public void setEID(int eID) {
        this.eID = eID;
    }

    public String getEfirstName() {
        return efirstName;
    }

    public void setEfirstName(String efirstName) {
        this.efirstName = efirstName;
    }

    public String getElastName() {
        return elastName;
    }

    public void setElastName(String elastName) {
        this.elastName = elastName;
    }

    public String getEEmail() {
        return eEmail;
    }

    public void setEEmail(String eEmail) {
        this.eEmail = eEmail;
    }

    public String getEPhone() {
        return ePhone;
    }

    public void setEPhone(String ePhone) {
        this.ePhone = ePhone;
    }

    public String getEHireDate() {
        return eHireDate;
    }

    public void setEHireDate(String eHireDate) {
        this.eHireDate = eHireDate;
    }

    public String getEJobID() {
        return eJobID;
    }

    public void setEJobID(String eJobID) {
        this.eJobID = eJobID;
    }

    public float getESalary() {
        return eSalary;
    }

    public void setESalary(float eSalary) {
        this.eSalary = eSalary;
    }

    public float getECommPct() {
        return eCommPct;
    }

    public void setECommPct(float eCommPct) {
        this.eCommPct = eCommPct;
    }

    public int getEMgrID() {
        return eMgrID;
    }

    public void setEMgrID(int eMgrID) {
        this.eMgrID = eMgrID;
    }

    public int getEDepID() {
        return eDepID;
    }

    public void setEDepID(int eDepID) {
        this.eDepID = eDepID;
    }

    @Override
    public String toString() {
        return "EmpRecord{" + "eID=" + eID + ", efirstName=" + efirstName + 
                ", elastName=" + elastName + ", eEmail=" + eEmail + ", ePhone=" 
                + ePhone + ", eHireDate=" + eHireDate + ", eJobID=" + eJobID + 
                ", eSalary=" + eSalary + ", eCommPct=" + eCommPct + ", eMgrID=" 
                + eMgrID + ", eDepID=" + eDepID + '}';
    }

    


  
    
}


