/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;
//import java.sql.*;
//import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author meghanaraob
 */
public class SQL {
Connection conn=null;

/*public SQL(String URL, String driver, String user, String password){
    try{
        Class.forName(driver);
        conn=DriverManager.getConnection(URL, user, password);
    } catch(Exception e){
        JOptionPane.showMessageDialog(null, e);

    }
}*/
public static Connection ConnectDB(){
try{
    /*Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection conn=DriverManager.getConnection("jdbc:oracle:thin:testuser/testpass@localhost:1521/XE");
    JOptionPane.showMessageDialog(null, "Connection to database established");
    return conn;*/
     Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
        //JOptionPane.showMessageDialog(null, "Connection to database established");
        return con;
}
catch(Exception e){
    JOptionPane.showMessageDialog(null, "Databsse connectivity unsuccessful");
    return null;
}
}
}

