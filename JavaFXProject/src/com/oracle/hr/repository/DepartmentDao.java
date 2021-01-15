package com.oracle.hr.repository;

import com.oracle.hr.bean.Department;

public interface DepartmentDao {
    Department getDepartmentByDepartmentName(String deptName);
}
