package com.oracle.hr.service;

import com.oracle.hr.bean.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeeList();
    Employee findEmployeeByEmpId(int id);
    void createEmployee(Employee e);
    void updateEmployee(Employee e);
    void deleteEmployee(Employee e);
    List<Employee> getManagerList();
}
