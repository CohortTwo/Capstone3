package hr.model;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmp();
    Employee getEmployeeById(int empId);
    boolean addEmployee(Employee emp);
    boolean delEmployee(int empId);
    boolean updateEmployee(int empId, Employee Emp);
}

