package hr.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

public class EmployeeDAOImpl implements EmployeeDAO {
	List<Employee> employees;

	public EmployeeDAOImpl() {
		employees = new ArrayList<>();
	}

	@Override
    public List<Employee> getAllEmp() {
		Connection conn = connectDB();
		try {
			ResultSet rs = conn.createStatement()
				.executeQuery("select * from employees");
			if (rs.isBeforeFirst()) {
				 while (rs.next()) {
					employees.add(new Employee( 
								rs.getInt("employee_id"),
								rs.getString("first_name"),
								rs.getString("last_name"),
								rs.getString("email"),
								rs.getString("phone_number"),
								LocalDate.parse(
									rs.getString("hire_date"), 
									DateTimeFormatter.ISO_DATE),
								rs.getInt("job_id"),
								rs.getFloat("salary"),
								rs.getInt("manager_id"),
								rs.getInt("department_id")));
				 } 
				 return employees;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { conn.close(); }
			catch (SQLException ex) {}
		}
		return null;
	}

	@Override
	public Employee getEmployeeById(int empId) {
		Connection conn = connectDB();
		try {
			ResultSet rs = conn
				.createStatement()
				.executeQuery(
						"select * from employees where employee_id=" + empId);
			if (rs.isBeforeFirst()) {
				return new Employee(
						rs.getInt("employee_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("email"),
						rs.getString("phone_number"),
						LocalDate.parse(
							rs.getString("hire_date"), 
							DateTimeFormatter.ISO_DATE),
						rs.getInt("job_id"),
						rs.getFloat("salary"),
						rs.getInt("manager_id"),
						rs.getInt("department_id"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { conn.close(); }
			catch (SQLException ex) {}
		}
		return null;
	}


	@Override
	public boolean addEmployee(Employee emp) {
		Connection conn = connectDB();
		try {
			conn.createStatement() 
				.executeUpdate(
						"INSERT INTO employees " + 
						"(employee_id, first_name, last_name, email, " +
						" phone_number, hire_date, job_id, salary, " +
						" manager_id, department_id)" +
						"VALUES (" +
							emp.getEmployeeId() + ", '" +
						    emp.getFirstName() + "', '" +
						    emp.getLastName() + "', '" +
						    emp.getEmail() + "', '" +
						    emp.getPhoneNumber() + "', '" +
						    emp.getHireDate() + "', "  +
						    emp.getJobId() + ", "  +
						    emp.getSalary() + ", " +
						    emp.getManager_id() + ", " +
						    emp.getDepartment_id() + ")" 
						);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try { conn.close(); } 
			catch (SQLException ex) {}
		}
		return true;
	}

	@Override
	public boolean delEmployee(int empId) {
		Connection conn = connectDB();
		try {
			conn.createStatement().executeUpdate( 
					"DELETE FROM employees " + 
					"WHERE employee_id = " + empId);

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try { conn.close(); } 
			catch (SQLException ex) {}
		}
		return true;
	}

	@Override
	public boolean updateEmployee(int empId, Employee emp) {
		Connection conn = connectDB();
		try {
			conn.createStatement() 
				.executeUpdate(
						"UPDATE employees " + 
						"SET " +
						"first_name = '" + emp.getFirstName() + "', " +
						"last_name = '" + emp.getLastName() + "', " +
						"email = '" + emp.getEmail() + "', " +
						"phone_number = '" + emp.getPhoneNumber() + "', " +
						"hire_date = '" + emp.getHireDate() + "', "  +
						"job_id = " + emp.getJobId() + ", "  +
						"salary = " + emp.getSalary() + ", " +
						"manager_id = " + emp.getManager_id() + ", " +
						"department_id = " + emp.getDepartment_id() + " " +
						"WHERE employee_id = " + emp.getEmployeeId()
						);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try { conn.close(); } 
			catch (SQLException ex) {}
		}
		return true;
	}

	private Connection connectDB() {
		Connection conn=null; 
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlite:src/main/resources/hr.db");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
		return conn;
	}
}
