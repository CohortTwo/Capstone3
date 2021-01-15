package hr;

import java.io.IOException;

import hr.controller.Controller;
import hr.model.EmployeeDAO;
import hr.model.EmployeeDAOImpl;
import hr.view.View;

public class HR {
	public static void main(String... args) throws IOException {
		EmployeeDAO model = new EmployeeDAOImpl();
		View view = new View();
		Controller controller = new Controller(model, view);
	}
}
