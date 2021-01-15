package hr.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Button.Listener;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import hr.model.Employee;
import hr.model.EmployeeDAO;
import hr.model.Validation;
import hr.view.View;
import hr.view.View.EmployeeForm;

public class Controller {
	private EmployeeDAO model;
	public View view;

	public Controller(EmployeeDAO model, View view) throws IOException {
		this.model = model; 
		this.view = view; 

		// Set up the main window
		View.MainFrame mainFrame = this.view.new MainFrame();

		// Set up the main menu
		mainFrame.getMainMenu().addItem("Display all employees", empTable);
		mainFrame.getMainMenu().addItem("Delete/Update employees", empById);
		mainFrame.getMainMenu().addItem("Add employee", empAdd);
		mainFrame.getMainMenu().addItem("Exit", closeWindow);

		// Load the menu into the main window and show
		this.view.getGui().addWindowAndWait(mainFrame);
	}

	private Runnable closeWindow = () -> {
		System.exit(0);
	};

	private Button.Listener close = b -> {
		view.getGui().getActiveWindow().close();
	};

	private Runnable empAdd = () -> {
		View.EmployeeForm empForm = view.new EmployeeForm();
		view.getGui().addWindow(empForm);

		empForm.getEmpId().setValidationPattern(Validation.NUMBERS_ONLY);
		empForm.getFirstName().setValidationPattern(Validation.LETTERS_ONLY);
		empForm.getLastName().setValidationPattern(Validation.LETTERS_ONLY);
		empForm.getPhone().setValidationPattern(Validation.PHONE_NUMBER);
		empForm.getHireYear().setValidationPattern(Validation.YEAR);
		empForm.getHireMonth().setValidationPattern(Validation.MONTH);
		empForm.getHireDay().setValidationPattern(Validation.DAY);
		empForm.getSalary().setValidationPattern(Validation.CURRENCY);
		empForm.getManagerId().setValidationPattern(Validation.NUMBERS_ONLY);

		empForm.getCancelButton().addListener(close);
		empForm.getSubmitButton().addListener(b -> {
			Employee newEmp = Employee.formToEmployee(empForm);
			if (model.addEmployee(newEmp)) {
				MessageDialog.showMessageDialog(view.getGui(), "Record added", "Record successful added");
				empForm.resetForm();
				empForm.getEmpId().takeFocus();
			} else {
				MessageDialog.showMessageDialog(view.getGui(), "Warning", "Errors occurred during operation");
			}
		});
	};

	private Runnable empTable = () -> {
		View.EmployeeTable table = view.new EmployeeTable(model.getAllEmp());
		table.getCloseButton().addListener(close);
		view.getGui().addWindowAndWait(table);
	};
	
	private Button.Listener delEmp = b -> {
		int empId = Integer.parseInt(b.getLabel());
		if (model.delEmployee(empId)) {
			MessageDialog.showMessageDialog(
					view.getGui(), 
					"Record deleted", 
					"Record successfully deleted");
			view.getGui().getActiveWindow().close(); // close EmployeeDisp
			view.getGui().getActiveWindow().close(); // close EmployeeSearchForm
		} else {
			MessageDialog.showMessageDialog(
					view.getGui(), 
					"Warning", 
					"Errors occurred during operation");
			view.getGui().getActiveWindow().close(); // close EmployeeDisp
			view.getGui().getActiveWindow().close(); // close EmployeeSearchForm
		}
		b.setLabel("Delete");
	};

	private Button.Listener editEmp = b -> {
		int empId = Integer.parseInt(b.getLabel());
		Employee emp = model.getEmployeeById(empId);
		EmployeeForm editForm = view.new EmployeeForm();

		editForm.getSubmitButton().setLabel("Update");
		editForm.getEmpId().setText(String.valueOf(emp.getEmployeeId()));
		editForm.getFirstName().setText(emp.getFirstName());
		editForm.getLastName().setText(emp.getLastName());
		editForm.getEmail().setText(emp.getEmail());
		editForm.getPhone().setText(emp.getPhoneNumber());
		editForm.getHireYear().setText(String.valueOf(emp.getHireDate().getYear()));
		editForm.getHireMonth().setText(String.valueOf(emp.getHireDate().getMonthValue()));
		editForm.getHireDay().setText(String.valueOf(emp.getHireDate().getDayOfMonth()));
		editForm.getJobId().setSelectedIndex(emp.getJobId() - 1);
		editForm.getSalary().setText(String.valueOf(emp.getSalary()));
		editForm.getManagerId().setText(String.valueOf(emp.getManager_id()));
		editForm.getDeptId().setSelectedIndex(emp.getDepartment_id() - 1);

		editForm.getCancelButton().addListener(close);

		editForm.getEmpId().setValidationPattern(Validation.NUMBERS_ONLY);
		editForm.getFirstName().setValidationPattern(Validation.LETTERS_ONLY);
		editForm.getLastName().setValidationPattern(Validation.LETTERS_ONLY);
		editForm.getPhone().setValidationPattern(Validation.PHONE_NUMBER);
		editForm.getHireYear().setValidationPattern(Validation.YEAR);
		editForm.getHireMonth().setValidationPattern(Validation.MONTH);
		editForm.getHireDay().setValidationPattern(Validation.DAY);
		editForm.getSalary().setValidationPattern(Validation.CURRENCY);
		editForm.getManagerId().setValidationPattern(Validation.NUMBERS_ONLY);

		editForm.getEmpId().setReadOnly(true);

		editForm.getCancelButton().addListener(close);
		
		editForm.getSubmitButton().addListener(k -> {
			Employee updatedEmp = Employee.formToEmployee(editForm);
			if (model.updateEmployee(empId, updatedEmp)) {
				MessageDialog.showMessageDialog(view.getGui(), "Record updated", "Record successful updated");
				view.getGui().removeWindow(editForm);
				view.getGui().getActiveWindow().close(); // remove EmployeeDisp
				view.getGui().getActiveWindow().close(); // remove EmployeeSearchForm
			} else {
				MessageDialog.showMessageDialog(view.getGui(), "Warning", "Errors occurred during operation");
				view.getGui().removeWindow(editForm);
				view.getGui().getActiveWindow().close(); // remove EmployeeDisp
				view.getGui().getActiveWindow().close(); // remove EmployeeSearchForm
			}
		});
			
		view.getGui().addWindow(editForm);

		b.setLabel("Edit");
	};

	private Runnable empById = () -> {
		View.EmployeeSearchForm searchForm = view.new EmployeeSearchForm();

		searchForm.getCloseButton().addListener(close);

		searchForm.getSearchButton().addListener(b -> {
			String rawTarget = searchForm.getSearchBox().getText().trim();
			Integer target = rawTarget.equals("") ? 
				null : Integer.parseInt(rawTarget);
			Employee emp = target == null ? 
				null : model.getEmployeeById(target);

					
			if (emp == null) {
				new MessageDialogBuilder() 
					.setTitle("Warning")
					.setText("Employee not found")
					.build()
					.showDialog(view.getGui());
				searchForm.getSearchBox().takeFocus();
			} else {
				View.EmployeeDisp empDisp = view.new EmployeeDisp(emp);
				empDisp.getCloseButton().addListener(close);
				view.getGui().addWindow(empDisp);

				/* Delete button callback */
				// Tag the employeeId onto the button label.
				empDisp.getDelete().addListener(k -> { 
					empDisp
						.getDelete()
						.setLabel(String.valueOf(emp.getEmployeeId()));
				});
				// This is the real callback function.
				empDisp.getDelete().addListener(delEmp);

				/* edit button callback */
				// Tag the employeeId onto the button label. 
				empDisp.getEdit().addListener(k -> {
					empDisp
						.getEdit()
						.setLabel(String.valueOf(emp.getEmployeeId()));
				});
				// This is the real callback function. 
				empDisp.getEdit().addListener(editEmp);
			}
		});
		view.getGui().addWindowAndWait(searchForm);
	};
}
