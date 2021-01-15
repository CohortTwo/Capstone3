package com.oracle.hr.repository;

import com.oracle.hr.bean.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDaoImpl implements DepartmentDao {

    private Connection connection;

    public DepartmentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Department getDepartmentByDepartmentName(String deptName){

        String query = "select * from departments where department_name = ?";
        ResultSet resultSet = null;
        Department department = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1,deptName);

            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet.getMetaData().getColumnName(1));
            while(resultSet.next()){

                 department= new Department(resultSet.getInt("department_id"),
                        resultSet.getString("department_name"),
                        resultSet.getInt("manager_id"),
                        resultSet.getInt("location_id")
                        );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return department;
    }
}
