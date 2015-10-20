/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BinhNguyen
 */
public class DatabaseConnection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        
        if (connectDB()!=null){
            System.out.println("Connect to TestDB successful!");
        }else System.out.println("Connect to TestDB fail!");
        getData();
    }
     public static Connection connectDB() {
        Connection conn = null;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String user="sa";
            String pass="1234$";
            String sql="jdbc:sqlserver://localhost:1433;databaseName=TestDB";
            try {
                conn = DriverManager.getConnection(sql, user, pass);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
     
    public static void getData(){
        try {
            Connection con = connectDB();
            Statement st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT * FROM Users");
            while ( rs.next() ) {
                String username = rs.getString("Username");
                System.out.println(username);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
