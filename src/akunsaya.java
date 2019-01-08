import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class akunsaya {

	protected Shell shlAkunSaya;
	private Text inputKTP;
	private Button btnSimpan;
	private Label lblAkunSaya;
	private Label lblDeskripsiDaftar;
	private Button btnKembali;
	private static userClass user = new userClass();
	
	//DATABASE KONEKSI
	private koneksi connect = new koneksi();
	private Statement stmt = null;
	private ResultSet rs = null;
	private Text inputEmail;
	private Text inputPassword;
	private Text inputNama;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			akunsaya window = new akunsaya();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open(String namauser, String emailuser, String iduser) {
		Display display = Display.getDefault();
		createContents(namauser,emailuser,iduser);
		shlAkunSaya.open();
		shlAkunSaya.layout();
		while (!shlAkunSaya.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String namauser, String emailuser, String iduser) {
		shlAkunSaya = new Shell(SWT.TITLE | SWT.CLOSE | SWT.BORDER);
		shlAkunSaya.setSize(560, 440);
		shlAkunSaya.setText("EDIT AKUN");
		
		inputNama = new Text(shlAkunSaya, SWT.BORDER);
		inputNama.setText(namauser);
		inputNama.setMessage("Masukan Nama Anda");
		inputNama.setEnabled(false);
		inputNama.setBounds(252, 129, 282, 24);
		
		inputEmail = new Text(shlAkunSaya, SWT.BORDER);
		inputEmail.setBounds(252, 161, 282, 24);
		inputEmail.setMessage("Masukan email anda");
		inputEmail.setText(emailuser);
		inputEmail.setEnabled(false);
		
		inputPassword = new Text(shlAkunSaya, SWT.BORDER);
		inputPassword.setBounds(252, 191, 282, 24);
		inputPassword.setMessage("Masukan password anda");
		inputPassword.setText(userData(emailuser,"password"));
		
		inputKTP = new Text(shlAkunSaya, SWT.BORDER);
		inputKTP.setBounds(252, 221, 282, 24);
		inputKTP.setMessage("Masukan nomor KTP anda");
		inputKTP.setText(userData(emailuser,"noktp"));

		
		Label labelEditAkun = new Label(shlAkunSaya, SWT.NONE);
		labelEditAkun.setAlignment(SWT.CENTER);
		labelEditAkun.setBounds(252, 79, 282, 54);
		labelEditAkun.setText("Anda hanya dapat mengedit \n Email, Password dan KTP");
		
		btnSimpan = new Button(shlAkunSaya, SWT.NONE);
		btnSimpan.setBounds(340, 251, 85, 26);
		btnSimpan.setText("SIMPAN");
		btnSimpan.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String no_ktp = inputKTP.getText();
				String email = inputEmail.getText();
				String password = inputPassword.getText();
				try {
					prosesEdit(email,password,no_ktp,iduser);
				} catch (NullPointerException e2) {
					MessageBox dialog = new MessageBox(shlAkunSaya, SWT.ICON_ERROR | SWT.OK);
		    		dialog.setMessage("Form tidak boleh kosong");
		    		dialog.open();
				}
			}
		});
		
		lblDeskripsiDaftar = new Label(shlAkunSaya, SWT.NONE);
		lblDeskripsiDaftar.setAlignment(SWT.CENTER);
		lblDeskripsiDaftar.setBounds(1, 161, 226, 21);
		lblDeskripsiDaftar.setText("Edit Akun");
		
		btnKembali = new Button(shlAkunSaya, SWT.NONE);
		btnKembali.setBounds(41, 188, 143, 26);
		btnKembali.setText("KEMBALI");
		btnKembali.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlAkunSaya.close();
				mainlogin ml = new mainlogin();
				ml.open(namauser,emailuser,iduser);
			}
		});
		
		Label lblSeparator = new Label(shlAkunSaya, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparator.setText("Separator");
		lblSeparator.setBounds(233, 37, 1, 290);
		
		lblAkunSaya = new Label(shlAkunSaya, SWT.NONE);
		lblAkunSaya.setAlignment(SWT.CENTER);
		lblAkunSaya.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblAkunSaya.setBounds(36, 129, 148, 36);
		lblAkunSaya.setText("Akun Saya");
	}
	
	public void prosesEdit(String email, String password, String no_ktp, String iduser) {
		try{
			 Class.forName("com.mysql.cj.jdbc.Driver");  
			   Connection con=DriverManager.getConnection(  
			     "jdbc:mysql://localhost:3306/sewamobil","bayu","123qwe");
			   String sql = "UPDATE user_ SET EMAIL = '"+email+"', PASSWORD = '"+password+"', NO_KTP = '"+no_ktp+"' WHERE ID_USER = '"+iduser+"'";
				PreparedStatement prepare = con.prepareStatement(sql);
				
				if(email != null && !email.isEmpty() && password != null && !password.isEmpty() && no_ktp != null && !no_ktp.isEmpty()) {
					prepare.executeUpdate();
					MessageBox dialog1 = new MessageBox(shlAkunSaya, SWT.ICON_INFORMATION | SWT.OK);
		    		dialog1.setMessage("Berhasil! Akun anda berhasil diubah");
		    		dialog1.open();
					prepare.close();
				} else {
					MessageBox dialog2 = new MessageBox(shlAkunSaya, SWT.ICON_ERROR | SWT.OK);
		    		dialog2.setMessage("Gagal! Silahkan coba lagi");
		    		dialog2.open();
				}
			}
			catch(SQLException | ClassNotFoundException ex)
			{
				MessageBox dialog1 = new MessageBox(shlAkunSaya, SWT.ICON_ERROR | SWT.OK);
				dialog1.setMessage("Email sudah terdaftar");
	    		dialog1.open();
			}
	}
	
	public String userData(String email,String ask) {
		String cd = user.getUserData(email);
		String[] oke = cd.split("-");
		String namauser = oke[0];
		String iduser = oke[1];
		String passworduser = oke[2];
		String noktpuser = oke[3];
		String a = null;
		switch (ask) {
		case "nama": 
			a = namauser;
			break;
		case "id":
			a = iduser;
			break;
		case "password":
			a = passworduser;
			break;
		case "noktp":
			a = noktpuser;
			break;
		}
		return a;
	}
}
