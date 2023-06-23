package org.example;
import java.sql.*;

public class Database_Handler {
    private static final String DB_url = "jdbc:derby:database/forum;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
    public static Database_Handler handler;
    public static String tableName;
    public Database_Handler(){
        createConnection();
        createTable();
    }

    public static Database_Handler getHandler(){
        if(handler == null){
            handler = new Database_Handler();
            return handler;
        }else{
            return handler;
        }
    }

    private void createTable() {
        String TABLE_NAME = tableName;
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dmn = conn.getMetaData();
            ResultSet tables = dmn.getTables(null,null,TABLE_NAME,null);
            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + " exists");
            }
            else{
                String statement = "CREATE TABLE " + TABLE_NAME + " ("
                        + "name varchar(200) primary key, \n"
                        + "path varchar(200), \n"
                        + "extension varchar(200), \n"
                        + "size varchar(200))";

                stmt.execute(statement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createConnection() {
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean execAction(String qu) {
        try{
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException throwables) {
            System.out.println(throwables);
            System.out.println("Did not enter data");
        }
        return false;
    }

    public ResultSet execQuery(String query) {
        ResultSet resultSet;
        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return resultSet;
    }

    public void displayTable() {
        try {
            DatabaseMetaData dmn = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dmn.getTables(null, null, "%", types);
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
