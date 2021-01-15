package com.oracle.hr.service;

import com.oracle.hr.bean.Department;

public interface DepartmentService {
    Department findDepartmentFromDepartmentName(String deptName);
}
