package com.oracle.hr.repository;

import com.oracle.hr.bean.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getAllEmployees();
    Employee findEmployeeByEmployeeId(int id);
    void updateEmployee(Employee employee);
    void insertEmployee(Employee employee);
    void deleteEmployee(Employee employee);

}
