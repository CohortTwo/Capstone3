package com.oracle.hr.service;

import com.oracle.hr.bean.Department;
import com.oracle.hr.repository.DepartmentDao;
import com.oracle.hr.repository.DepartmentDaoImpl;
import com.oracle.hr.repository.JdbcConnection;

import java.sql.SQLException;

public class DepartmentServiceImpl implements DepartmentService {

    private static DepartmentService departmentService = null;
    private DepartmentDao departmentDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }


    public static DepartmentService getInstance() {
        if(departmentService == null){
            try {
                departmentService = new DepartmentServiceImpl(new DepartmentDaoImpl(JdbcConnection.getConnection()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return departmentService;
    }

    public Department findDepartmentFromDepartmentName(String deptName){
        return departmentDao.getDepartmentByDepartmentName(deptName);
    }
}
