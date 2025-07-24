package databaseLayer;

import java.sql.*;

public class DatabaseConnection {
    private final String URL = "jdbc:mysql://localhost:3306/medicare";
    private final String username = "root";
    private final String password = "";

    // Singleton pattern for database connection
    private static DatabaseConnection instance;

    // Connection object to establish connection with database
    private Connection connection;

    // ResultSet object to store the result of the SQL query execution
    public ResultSet resultSet;

    // PreparedStatement object to execute the SQL query
    public PreparedStatement preparedStatement;

    // Constructor to establish connection with database
    private DatabaseConnection() {
        try {
            // Load the MySQL driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found" + e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Connection failed" + e.getMessage());
        }
    }

    // Singleton pattern for database connection
    public static DatabaseConnection getSingleInstance() {
        try{
            if (instance == null) {
                instance = new DatabaseConnection();
            }
            else if (instance.connection.isClosed()) {
                instance = new DatabaseConnection();
            }
            else{
                return instance;
            }
            return instance;
        }
        catch(SQLException e){
            System.out.println("Connection failed" + e.getMessage());
            return null;
        }
    }

    // Execute SQL query
    public boolean ExecuteSQL(String sqlQuery) {
        try {
            // Create a statement object to execute the SQL query
            Statement statement = connection.createStatement();
            // Execute the SQL query and store the result in result variable
            int result = statement.executeUpdate(sqlQuery);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("SQL execution failed" + e.getMessage());
            return false;
        }
    }

    // Set prepared statement for SQL query execution
    public void setPreparedStatement(String sql) {
        try {
            // Create a prepared statement object to execute the SQL query
            preparedStatement = connection.prepareStatement(sql);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Execute prepared statement for SQL query execution
    public ResultSet ExecutePreparedStatement()
    {
        try
        {
            // Execute the prepared statement and store the result in result variable
            resultSet = preparedStatement.executeQuery();

            // Check if the result is empty
            if (!resultSet.next()) {
                System.out.println("Result not found");
                return null;
            } else {
                // Return the result of the query execution
                return resultSet;
            }
        }catch (SQLException ex)
        {
            System.out.println("SQL Error "+ex.getMessage());
            return resultSet;
        }
    }
}
