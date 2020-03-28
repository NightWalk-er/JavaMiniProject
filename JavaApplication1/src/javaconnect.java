/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mine
 */
import java.sql.*;
import javax.swing.JOptionPane;


public class javaconnect {
    Connection conn;
    
    public static Connection ConnecrDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounts?zeroDateTimeBehavior=CONVERT_TO_NULL","root","deepanshu@devil");
            return conn;
        } catch ( ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
}
