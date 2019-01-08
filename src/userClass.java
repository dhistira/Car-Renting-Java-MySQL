import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class userClass {

	public static boolean prosesLogin(String email, String password) {
		Shell shell = new Shell();
		Boolean b = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			   Connection con=DriverManager.getConnection(  
			     "jdbc:mysql://localhost:3306/sewamobil","bayu","123qwe");
			   String sql = "select * from user_ where EMAIL = '"+email+"' and PASSWORD = '"+password+"'";
				PreparedStatement prepare = con.prepareStatement(sql);
				
				if(email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
					ResultSet a = prepare.executeQuery(sql);
					if(a.next() == true) {
						b = true;
					}else {
						MessageBox dialog2 = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			    		dialog2.setMessage("Username/Password Salah!");
			    		dialog2.open();
			    		b = false;
					}
				} else {
					MessageBox dialog2 = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		    		dialog2.setMessage("Username/Password harus diisi");
		    		dialog2.open();
		    		b = false;
				}
		} catch(ClassNotFoundException|SQLException|NullPointerException  e) {
			MessageBox dialog1 = new MessageBox(shell, SWT.ERROR | SWT.OK);
    		dialog1.setMessage("Gagal! Silahkan coba lagi");
    		dialog1.open();
    		b = false;
			
		}
		return b;
	}
	
	public static String getUserData(String email) {
		Shell shell = new Shell();
		String outputnya = null;
		String namauser = null;
		String iduser = null;
		String passworduser = null;
		String noktpuser = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			   Connection con=DriverManager.getConnection(  
			     "jdbc:mysql://localhost:3306/sewamobil","bayu","123qwe");
			   String sql = "select * from user_ where EMAIL = '"+email+"'";
				PreparedStatement prepare = con.prepareStatement(sql);
				ResultSet rs = prepare.executeQuery(sql);
				while(rs.next()) {
					namauser = rs.getString(2);
					iduser = rs.getString(1);
					passworduser = rs.getString(4);
					noktpuser = rs.getString(7);
				}
				outputnya = namauser+"-"+iduser+"-"+passworduser+"-"+noktpuser;
		} catch(ClassNotFoundException | SQLException | NullPointerException e) {
		}
		return outputnya;
	}
	
	public static String getCarData() {
		Shell shell = new Shell();
		String outputnya = null;
		String nama = null;
		String harga = null;
		String kapasitas = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			   Connection con=DriverManager.getConnection(  
			     "jdbc:mysql://localhost:3306/sewamobil","bayu","123qwe");
			   String sql = "select * from kendaraan ORDER BY PLAT_NO DESC";
				PreparedStatement prepare = con.prepareStatement(sql);
				ResultSet rs = prepare.executeQuery(sql);
				while(rs.next()) {
					nama = rs.getString(2);
					harga = rs.getString(3);
					kapasitas = rs.getString(4);
					
				}
				outputnya = nama+"-"+harga+"-"+kapasitas;
		} catch(ClassNotFoundException | SQLException | NullPointerException e) {
		}
		return outputnya;
	}

}
