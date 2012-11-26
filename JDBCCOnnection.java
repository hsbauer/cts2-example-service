package edu.mayo.cts2.framework.plugin.service.example;

/**
 * Created with IntelliJ IDEA.
 * Author: Scott Bauer bauer.scott@mayo.edu
 * Date: 11/2/12
 * Time: 8:23 AM
 */




import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JDBCCOnnection {
    Connection connection = null;

    public JDBCCOnnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    public Connection createConnection() {
        Connection con = null;
        if (connection != null) {
            System.out.println("Connection Failed");
        } else {
            try {
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/godata", "root",
                        "lexgrid");
                System.out.println("Connected");
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return con;
    }

    public Map<String, String> getResult(String text ) throws SQLException {
        Connection connection = createConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("select fullname, generic_url from db where name = " + "'" + text + "'");
        rs.next();
        String name = rs.getString("fullname");
        String url  = rs.getString("generic_url");
        Map<String, String> map = new HashMap <String, String>();
        map.put(name, url);
        connection.close();
        return  map;

    }

    public String getName(Map<String, String> map){
        return (String) map.keySet().toArray()[0];
    }

    public String getURI(Map<String, String> map){
       return (String) map.values().toArray()[0];
    }


    public static void main(String[] args) throws SQLException {
        JDBCCOnnection jcon = new JDBCCOnnection();
        Map<String, String> map = jcon.getResult("GO");
        String name = jcon.getName(map);
        String url = jcon.getURI(map);
        System.out.println(name);
        System.out.println(url);
    }
}
