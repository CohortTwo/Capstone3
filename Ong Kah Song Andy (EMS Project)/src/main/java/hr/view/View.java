package hr.view;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutData;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import hr.model.Employee;

public class View {
    private Screen screen;
    private WindowBasedTextGUI gui;
    public View() throws IOException {
        setScreen(new TerminalScreen(
                    new DefaultTerminalFactory().createTerminal()));
        setGui(new MultiWindowTextGUI(
                    screen, 
                    new DefaultWindowManager(), 
                    new EmptySpace(TextColor.ANSI.BLUE)));
        getScreen().startScreen();
    }

    public class MainFrame extends BasicWindow {
        private ActionListBox mainMenu;
        public MainFrame() {
            setTitle("Main Menu");
            setHints(Arrays.asList(Window.Hint.CENTERED));
            setMainMenu(new ActionListBox());
            setComponent(getMainMenu());
        }
        public ActionListBox getMainMenu() {
            return mainMenu;
        }
        private void setMainMenu(ActionListBox mainMenu) {
            this.mainMenu = mainMenu;
        }
    }

    public class EmployeeTable extends BasicWindow {
        List<Employee> empList;
        Button closeButton; 

        public EmployeeTable(List<Employee> empList) {
            this.empList = empList;
            setTitle("Employee List");
            setHints(Arrays.asList(Window.Hint.CENTERED));
            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
            Table<String> empTable = 
                new Table<>(
                        "Emp ID", 
                        "First Name", 
                        "Last Name", 
                        "Email", 
                        "Phone no.", 
                        "Hire Date", 
                        "Job ID", 
                        "Salary", 
                        "Manager ID", 
                        "Dept ID");
            for (Employee emp : empList) {
                empTable.getTableModel()
                    .addRow(
                            emp.getEmployeeId() + "", 
                            emp.getFirstName(),
                            emp.getLastName(), 
                            emp.getEmail(),
                            emp.getPhoneNumber(),
                            emp.getHireDate().toString(),
                            emp.getJobId() + "", 
                            emp.getSalary() + "", 
                            emp.getManager_id() + "",
                            emp.getDepartment_id() + ""
                    );
            }
            setCloseButton(new Button("Close"));
            getCloseButton().setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            panel.addComponent(empTable);
            panel.addComponent(getCloseButton());
            setComponent(panel);
        }

        public Button getCloseButton() {
            return closeButton;
        }

        private void setCloseButton(Button closeButton) {
            this.closeButton = closeButton;
        }
    }

    public class EmployeeSearchForm extends BasicWindow {
       private Button searchButton;
       private TextBox searchBox;
       private Button closeButton;

       public EmployeeSearchForm() {
            setTitle("Retrieve Employee's Record");
            setHints(Arrays.asList(Window.Hint.CENTERED));

            setSearchButton(new Button("Search"));
            setSearchBox(new TextBox());

            Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            Panel botPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            Panel topPanel = new Panel(new GridLayout(2));

            botPanel.setLayoutData(
                    LinearLayout.createLayoutData(
                        LinearLayout.Alignment.Center));
            
            Label empIdLabel = new Label("Employee ID:");
            setSearchBox(new TextBox(new TerminalSize(11, 1))
                .setValidationPattern(Pattern.compile("^[0-9]{1,10}$")));

            setCloseButton(new Button("Close"));

            topPanel.addComponent(empIdLabel);
            topPanel.addComponent(getSearchBox());

            botPanel.addComponent(getSearchButton());
            botPanel.addComponent(getCloseButton());

            mainPanel.addComponent(topPanel);
            mainPanel.addComponent(botPanel);

            setComponent(mainPanel);
        }

        public Button getCloseButton() {
            return closeButton;
        }

        private void setCloseButton(Button closeButton) {
            this.closeButton = closeButton;
        }

		public Button getSearchButton() {
            return searchButton;
        }

        private void setSearchButton(Button searchButton) {
            this.searchButton = searchButton;
        }

        public TextBox getSearchBox() {
            return searchBox;
        }

        private void setSearchBox(TextBox searchBox) {
            this.searchBox = searchBox;
        }
    }

    public class EmployeeDisp extends BasicWindow {
        private Button edit;
        private Button delete;
        private Button closeButton;

        public EmployeeDisp(Employee emp) {
            Panel mainPanel = 
                new Panel(new LinearLayout(Direction.VERTICAL));
            Panel bottomPanel = 
                new Panel(new LinearLayout(Direction.HORIZONTAL));

            bottomPanel.setLayoutData(
                    LinearLayout.createLayoutData(
                        LinearLayout.Alignment.Center));

            Label data = new Label(
                "Employee ID: " + emp.getEmployeeId() + "\n" +
                "First Name: " + emp.getFirstName() + "\n" +
                "Last Name: " + emp.getLastName() + "\n" +
                "Email: " + emp.getEmail() + "\n" +
                "Phone Number: " + emp.getPhoneNumber() + "\n" +
                "Hire Date: " + emp.getHireDate().toString() + "\n" +
                "Job ID: " + emp.getJobId() + "" + "\n" +
                "Salary: " + emp.getSalary() + "" + "\n" +
                "Manager ID: " + emp.getManager_id() + "" + "\n" +
                "Department ID: " + emp.getDepartment_id() + "");

            setEdit(new Button("Edit"));
            setDelete(new Button("Delete"));
            setCloseButton(new Button("Close"));

            bottomPanel.addComponent(getEdit());
            bottomPanel.addComponent(getDelete());
            bottomPanel.addComponent(getCloseButton());
            
            mainPanel.addComponent(data);
            mainPanel.addComponent(bottomPanel);
            
            setComponent(mainPanel);
        }

        public Button getEdit() {
            return edit;
        }

        private void setEdit(Button edit) {
            this.edit = edit;
        }

        public Button getDelete() {
            return delete;
        }

        private void setDelete(Button delete) {
            this.delete = delete;
        }

        public Button getCloseButton() {
            return closeButton;
        }

        public void setCloseButton(Button closeButton) {
            this.closeButton = closeButton;
        }
    }

    public class EmployeeForm extends BasicWindow {
        private Button submitButton; 
        private Button cancelButton; 
        private TextBox empId;
        private TextBox firstName;
        private TextBox lastName;
        private TextBox email;
        private TextBox phone;
        private TextBox hireYear;
        private TextBox hireMonth;
        private TextBox hireDay;
        private ComboBox<String> jobId;
        private TextBox salary;
        private TextBox managerId;
        private ComboBox<String> deptId;

        public EmployeeForm() {
            setTitle("Add Employee");
            setHints(Arrays.asList(Window.Hint.CENTERED));

            setSubmitButton(new Button("Submit"));
            setCancelButton(new Button("Cancel"));

            Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            Panel topPanel = new Panel(new GridLayout(2).setVerticalSpacing(1));
            Panel botPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

            final LayoutData RIGHT_ALIGN_GRID = GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER);
            final LayoutData CENTER_ALIGN_LINEAR = LinearLayout.createLayoutData(LinearLayout.Alignment.Center);

            botPanel.setLayoutData(CENTER_ALIGN_LINEAR);

            topPanel.addComponent(new Label("Employee ID: ")
                    .setLayoutData(RIGHT_ALIGN_GRID));
            setEmpId(new TextBox(new TerminalSize(15, 1)));
            topPanel.addComponent(getEmpId());

            topPanel.addComponent( 
                    new Label("First Name: ").setLayoutData(RIGHT_ALIGN_GRID));
            setFirstName(new TextBox(new TerminalSize(30, 1)));
            topPanel.addComponent(getFirstName());

            topPanel.addComponent(new Label("Last Name: ") 
                    .setLayoutData(RIGHT_ALIGN_GRID));
            setLastName(new TextBox(new TerminalSize(30, 1)));
            topPanel.addComponent(getLastName());

            topPanel.addComponent(new Label("Email: ")
                    .setLayoutData(RIGHT_ALIGN_GRID));
            setEmail(new TextBox(new TerminalSize(30, 1)));
            topPanel.addComponent(getEmail());

            topPanel.addComponent(new Label("Phone Number: ")
                    .setLayoutData(RIGHT_ALIGN_GRID));
            setPhone(new TextBox(new TerminalSize(15, 1)));
            topPanel.addComponent(getPhone());

            topPanel.addComponent(new Label("Hire Date (YYYY-MM-DD): "));
            setHireYear(new TextBox(new TerminalSize(5, 1)));
            setHireMonth(new TextBox(new TerminalSize(3, 1)));
            setHireDay(new TextBox(new TerminalSize(3, 1)));
            Panel panelDate = new Panel(new LinearLayout(Direction.HORIZONTAL));
            panelDate.addComponent(getHireYear());
            panelDate.addComponent(new Label("-"));
            panelDate.addComponent(getHireMonth());
            panelDate.addComponent(new Label("-"));
            panelDate.addComponent(getHireDay());
            topPanel.addComponent(panelDate);

            topPanel.addComponent(
                    new Label("Job ID: ").setLayoutData(RIGHT_ALIGN_GRID));
            setJobId(new ComboBox<String>());

            getJobId().addItem("1 : Public Accountant");
            getJobId().addItem("2 : Accounting Manager");
            getJobId().addItem("3 : Administration Assistant");
            getJobId().addItem("4 : President");
            getJobId().addItem("5 : Administration Vice President");
            getJobId().addItem("6 : Accountant");
            getJobId().addItem("7 : Finance Manager");
            getJobId().addItem("8 : Human Resources Representative");
            getJobId().addItem("9 : Programmer");
            getJobId().addItem("10: Marketing Manager");
            getJobId().addItem("11: Marketing Representative");
            getJobId().addItem("12: Purchasing Manager");
            getJobId().addItem("13: Public Relations Representative");
            getJobId().addItem("14: Purchasing Clerk");
            getJobId().addItem("15: Sales Manager");
            getJobId().addItem("16: Sales Representative");
            getJobId().addItem("17: Shipping Clerk");
            getJobId().addItem("18: Stock Clerk");
            getJobId().addItem("19: Stock Manager");

            topPanel.addComponent(getJobId().withBorder(Borders.singleLine()));

            topPanel.addComponent(new Label("Salary: ")
                    .setLayoutData(RIGHT_ALIGN_GRID));
            setSalary(new TextBox(new TerminalSize(15, 1)));
            topPanel.addComponent(getSalary());
                
            topPanel.addComponent(
                    new Label("Manager ID: ").setLayoutData(RIGHT_ALIGN_GRID));
            setManagerId(new TextBox(new TerminalSize(15, 1)));
            topPanel.addComponent(getManagerId());

            topPanel.addComponent(
                    new Label("Department ID: ").setLayoutData(RIGHT_ALIGN_GRID));
            setDeptId(new ComboBox<String>());

            getDeptId().addItem("1 : Administration");
            getDeptId().addItem("2 : Marketing");
            getDeptId().addItem("3 : Purchasing");
            getDeptId().addItem("4 : Human Resources");
            getDeptId().addItem("5 : Shipping");
            getDeptId().addItem("6 : Purchasing");
            getDeptId().addItem("7 : Human Resources");
            getDeptId().addItem("8 : Shipping");
            getDeptId().addItem("9 : IT");
            getDeptId().addItem("10: Public Relations");
            getDeptId().addItem("11: Accounting");

            topPanel.addComponent(getDeptId().withBorder(Borders.singleLine()));

            botPanel.addComponent(getSubmitButton());
            botPanel.addComponent(getCancelButton());

            mainPanel.addComponent(topPanel);
            mainPanel.addComponent(botPanel);

            setComponent(mainPanel);
        }

        public void resetForm() { 
            getEmpId().setText("");
            getFirstName().setText("");
            getLastName().setText("");
            getEmail().setText("");
            getPhone().setText("");
            getHireYear().setText("");
            getHireMonth().setText("");
            getHireDay().setText("");
            getJobId().setSelectedIndex(0);
            getSalary().setText("");
            getManagerId().setText("");
            getDeptId().setSelectedIndex(0);
        }

		public Button getCancelButton() {
			return cancelButton;
		}

		public void setCancelButton(Button cancelButton) {
			this.cancelButton = cancelButton;
		}

		public TextBox getEmpId() {
            return empId;
        }

        private void setEmpId(TextBox empId) {
            this.empId = empId;
        }

        public TextBox getFirstName() {
            return firstName;
        }

        private void setFirstName(TextBox firstName) {
            this.firstName = firstName;
        }

        public TextBox getLastName() {
            return lastName;
        }

        private void setLastName(TextBox lastName) {
            this.lastName = lastName;
        }

        public TextBox getEmail() {
            return email;
        }

        private void setEmail(TextBox email) {
            this.email = email;
        }

        public TextBox getPhone() {
            return phone;
        }

        private void setPhone(TextBox phone) {
            this.phone = phone;
        }

        public TextBox getHireYear() {
            return hireYear;
        }

        private void setHireYear(TextBox hireYear) {
            this.hireYear = hireYear;
        }

        public TextBox getHireMonth() {
            return hireMonth;
        }

        private void setHireMonth(TextBox hireMonth) {
            this.hireMonth = hireMonth;
        }

        public TextBox getHireDay() {
            return hireDay;
        }

        private void setHireDay(TextBox hireDay) {
            this.hireDay = hireDay;
        }

        public ComboBox<String> getJobId() {
            return jobId;
        }

        private void setJobId(ComboBox<String> jobId) {
            this.jobId = jobId;
        }

        public TextBox getSalary() {
            return salary;
        }

        private void setSalary(TextBox salary) {
            this.salary = salary;
        }

        public TextBox getManagerId() {
            return managerId;
        }

        private void setManagerId(TextBox managerId) {
            this.managerId = managerId;
        }

        public ComboBox<String> getDeptId() {
            return deptId;
        }

        private void setDeptId(ComboBox<String> deptId) {
            this.deptId = deptId;
        }

        public Button getSubmitButton() {
            return submitButton;
        }

        private void setSubmitButton(Button submitButton) {
            this.submitButton = submitButton;
        }
    }

    public Screen getScreen() {
        return screen;
    }

    private void setScreen(Screen screen) {
        this.screen = screen;
    }

    public WindowBasedTextGUI getGui() {
        return gui;
    }

    private void setGui(WindowBasedTextGUI gui) {
        this.gui = gui;
    }
}
