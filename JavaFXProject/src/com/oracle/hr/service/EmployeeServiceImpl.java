package com.oracle.hr.service;

import com.oracle.hr.bean.Employee;
import com.oracle.hr.repository.EmployeeDao;
import com.oracle.hr.repository.EmployeeDaoImpl;
import com.oracle.hr.repository.JdbcConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }

    private EmployeeDao employeeDao;
    private static EmployeeService employeeService = null;

    public static EmployeeService getInstance() {
        if(employeeService == null){
            try {
                employeeService = new EmployeeServiceImpl(new EmployeeDaoImpl(JdbcConnection.getConnection()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return employeeService;
    }


    public List<Employee> getEmployeeList(){
        return employeeDao.getAllEmployees();
    }

    public Employee findEmployeeByEmpId(int id){
        return employeeDao.findEmployeeByEmployeeId(id);
    }

    public void createEmployee(Employee e){
        employeeDao.insertEmployee(e);
    }

    public void updateEmployee(Employee e) {
        employeeDao.updateEmployee(e);
    }

    public void deleteEmployee(Employee e){
        employeeDao.deleteEmployee(e);
    }

    public List<Employee> getManagerList() {
        List<Employee> employees = employeeDao.getAllEmployees();
        Set<Integer> managerIdList = employees.stream().map(e -> e.getManagerId()).collect(Collectors.toSet());
        return employees.stream().filter(e -> managerIdList.contains(e.getEmployeeId())).collect(Collectors.toList());
    }

}
