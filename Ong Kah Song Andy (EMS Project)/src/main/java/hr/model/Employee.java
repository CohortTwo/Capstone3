package hr.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hr.view.View.EmployeeForm;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private int jobId;
    private float salary;
    private Integer manager_id;
    private int department_id;

    public Employee(
            int employeeId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate hireDate,
            int jobId,
            float salary,
            Integer manager_id,
            int department_id) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.jobId = jobId;
        this.salary = salary;
        this.manager_id = manager_id;
        this.department_id = department_id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    @Override
    public String toString() {
        return "Employee [department_id=" + department_id + ", email=" + email + ", employeeId=" + employeeId
                + ", firstName=" + firstName + ", hireDate=" + hireDate + ", jobId=" + jobId + ", lastName=" + lastName
                + ", manager_id=" + manager_id + ", phoneNumber=" + phoneNumber + ", salary=" + salary + "]";
    }

	public static Employee formToEmployee(EmployeeForm form) {
        /* manager_id is column that can be null */
		String rawManagerId = form.getManagerId().getText().trim();
		Integer managerId = rawManagerId.equals("") ? 
			null : Integer.valueOf(rawManagerId);

        /* LocalDate.parse() will not parse single character day or month. For
         * example, 1 has to be 01, 5 be 05...etc. */
        String month = form.getHireMonth().getText();
        String day = form.getHireDay().getText();
        month = month.length() == 1 ? "0" + month : month;
        day = day.length() == 1 ? "0" + day : day;

		return new Employee(
			Integer.valueOf(form.getEmpId().getText()), 
			form.getFirstName().getText(),              	
			form.getLastName().getText(),               	
			form.getEmail().getText(),                  
			form.getPhone().getText(), 
			LocalDate.parse(
				form.getHireYear().getText() + "-" + 
				month + "-" + day, 
				DateTimeFormatter.ISO_DATE),
			form.getJobId().getSelectedIndex() + 1, 
			Float.valueOf(form.getSalary().getText()), 
			managerId, // Either an Integer obj or null
			form.getDeptId().getSelectedIndex() + 1 );
	}
}
