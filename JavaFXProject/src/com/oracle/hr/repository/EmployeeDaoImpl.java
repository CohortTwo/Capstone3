package com.oracle.hr.repository;

import com.oracle.hr.bean.Department;
import com.oracle.hr.bean.Employee;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao{

    private Connection connection;

    public EmployeeDaoImpl(Connection connection){
        this.connection = connection;
    }

    public List<Employee> getAllEmployees(){
        List<Employee> employeeList = new ArrayList<>();

        String query = "select * from employees inner join departments on employees.department_id = departments.department_id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();

            cachedRowSet.populate(resultSet);

            while(cachedRowSet.next()){
                Department d = new Department(
                        cachedRowSet.getInt("employee_id"),
                        cachedRowSet.getString("department_name"),
                        cachedRowSet.getInt("manager_id"),
                        cachedRowSet.getInt("location_id")
                );
                Employee e = new Employee(
                        cachedRowSet.getInt("employee_id"),
                        cachedRowSet.getString("first_name"),
                        cachedRowSet.getString("last_name"),
                        cachedRowSet.getString("email"),
                        cachedRowSet.getString("phone_number"),
                        cachedRowSet.getDate("hire_date"),
                        cachedRowSet.getString("job_id"),
                        cachedRowSet.getInt("salary"),
                        cachedRowSet.getDouble("commission_pct"),
                        cachedRowSet.getInt("manager_id"),
                        cachedRowSet.getInt("department_id"),
                        d
                );

                employeeList.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    public List<Employee> getAllEmployeesUsingJdbcRowSet(){

        List<Employee> employeeList = new ArrayList<>();

        try
                (JdbcRowSet jdbcRowSet = RowSetProvider.newFactory().createJdbcRowSet();)
        {
            jdbcRowSet.setUrl("jdbc:oracle:thin:@localhost:8888/ORCLPDB1.localdomain");
            jdbcRowSet.setUsername("hr");
            jdbcRowSet.setPassword("hr");
            jdbcRowSet.setCommand("select * from employees");
            jdbcRowSet.execute();

            jdbcRowSet.addRowSetListener(new RowSetListener() {
                @Override
                public void rowSetChanged(RowSetEvent rowSetEvent) {
                    System.out.println("Cursor Moved");
                }

                @Override
                public void rowChanged(RowSetEvent rowSetEvent) {
                    System.out.println("Cursor Changed");
                }

                @Override
                public void cursorMoved(RowSetEvent rowSetEvent) {
                    System.out.println("Rowset Changed");
                }
            });

            while (jdbcRowSet.next()){

                Employee e = new Employee(
                        jdbcRowSet.getInt("employee_id"),
                        jdbcRowSet.getString("first_name"),
                        jdbcRowSet.getString("last_name"),
                        jdbcRowSet.getString("email"),
                        jdbcRowSet.getString("phone_number"),
                        jdbcRowSet.getDate("hire_date"),
                        jdbcRowSet.getString("job_id"),
                        jdbcRowSet.getInt("salary"),
                        jdbcRowSet.getDouble("commission_pct"),
                        jdbcRowSet.getInt("manager_id"),
                        jdbcRowSet.getInt("department_id")
                );
                System.out.println("Employee end");
                employeeList.add(e);
                System.out.println("Employee added");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return employeeList;
    }

    public Employee findEmployeeByEmployeeId(int id) {

        Employee employee = null;

        String query = "select * from employees where employee_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)){

            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                employee = new Employee(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDate("hire_date"),
                        resultSet.getString("job_id"),
                        resultSet.getInt("salary"),
                        resultSet.getDouble("commission_pct"),
                        resultSet.getInt("manager_id"),
                        resultSet.getInt("department_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    public void updateEmployee(Employee employee) {

        String query = "update employees set " +
                " first_name = ? ," +
                " last_name = ? ," +
                " email = ? ," +
                " phone_number = ? ," +
                " hire_date = ? ," +
                " job_id = ? ," +
                " salary = ? ," +
                " commission_pct = ? ," +
                " manager_id = ? ," +
                " department_id = ? " +
                " where employee_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1,employee.getFirstName());
            statement.setString(2,employee.getLastName());
            statement.setString(3,employee.getEmail());
            statement.setString(4,employee.getPhoneNumber());
            statement.setDate(5, (Date) employee.getHireDate());
            statement.setString(6,employee.getJobId());
            statement.setInt(7,employee.getSalary());
            statement.setDouble(8,employee.getCommisionPct());
            statement.setInt(9,employee.getManagerId());
            statement.setInt(10,employee.getDepartmentId());
            statement.setInt(11,employee.getEmployeeId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void insertEmployee(Employee employee){

        String query = " insert into employees " +
                "(first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id)" +
                "values " +
                "(?,?,?,?,?,?,?,?,?,?)";

        try( PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getPhoneNumber());
            statement.setDate(5, new Date(employee.getHireDate().getTime()));
            statement.setString(6, employee.getJobId());
            statement.setInt(7, employee.getSalary());
            statement.setDouble(8, employee.getCommisionPct());
            statement.setInt(9, employee.getManagerId());
            statement.setInt(10, employee.getDepartmentId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void deleteEmployee(Employee employee){

        String query = "delete from employees where employee_id = ? ";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1,employee.getEmployeeId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}







