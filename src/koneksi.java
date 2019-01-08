/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.*;

public class koneksi { // koneksi ke database
	private static String url = "jdbc:mysql://localhost:3306/sewamobil";    
    private static String driverName = "com.mysql.cj.jdbc.Driver";   
    private static String username = "root";   
    private static String password = "";
    private static Connection con;
    private static String urlstring;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(urlstring, username, password);
                System.out.println("sukses");
            } catch (SQLException ex) {
                // log an exception. for example:
                System.out.println("Failed to create the database connection."); 
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found."); 
        }
        return con;
    }
}*/

import java.sql.*;  

public class koneksi{  
	private static Connection con;
 public static Connection getConnection(){  
  try{  
   Class.forName("com.mysql.cj.jdbc.Driver");  
   Connection con=DriverManager.getConnection(  
     "jdbc:mysql://localhost/sewamobil","bayu","123qwe");   
   Statement stmt=con.createStatement(); 
  }catch(Exception e){ 
	  System.out.println(e);
  }  
  return con;
 }  
}