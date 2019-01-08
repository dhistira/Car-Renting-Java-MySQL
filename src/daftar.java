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

public class daftar {

	protected Shell shlDaftar;
	private Text inputNama;
	private Text inputAlamat;
	private Text inputTelp;
	private Text inputKTP;
	private Button btnDaftar;
	private Label lblJudulDaftar;
	private Label lblDeskripsiDaftar;
	private Button btnKembali;
	
	
	//DATABASE KONEKSI
	private koneksi connect = new koneksi();
	private Statement stmt = null;
	private ResultSet rs = null;
	private Text inputEmail;
	private Text inputPassword;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			daftar window = new daftar();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlDaftar.open();
		shlDaftar.layout();
		while (!shlDaftar.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlDaftar = new Shell();
		shlDaftar.setSize(560, 440);
		shlDaftar.setText("DAFTAR");
		
		inputNama = new Text(shlDaftar, SWT.BORDER);
		inputNama.setBounds(252, 102, 282, 24);
		inputNama.setMessage("Masukan Nama Anda");
		
		inputAlamat = new Text(shlDaftar, SWT.BORDER);
		inputAlamat.setBounds(252, 132, 282, 61);
		inputAlamat.setMessage("Masukan Alamat Anda");
		
		inputEmail = new Text(shlDaftar, SWT.BORDER);
		inputEmail.setBounds(252, 199, 282, 24);
		inputEmail.setMessage("Masukan email anda");
		
		inputPassword = new Text(shlDaftar, SWT.BORDER);
		inputPassword.setBounds(252, 229, 282, 24);
		inputPassword.setMessage("Masukan password anda");
		
		inputTelp = new Text(shlDaftar, SWT.BORDER);
		inputTelp.setBounds(252, 259, 282, 24);
		inputTelp.setMessage("Masukan No Telp, Misal: 081xxxxx");
		
		inputKTP = new Text(shlDaftar, SWT.BORDER);
		inputKTP.setBounds(252, 289, 282, 24);
		inputKTP.setMessage("Masukan nomor KTP anda");

		
		Label labelDaftar = new Label(shlDaftar, SWT.NONE);
		labelDaftar.setAlignment(SWT.CENTER);
		labelDaftar.setBounds(252, 37, 282, 72);
		labelDaftar.setText("Dengan mengisi formulir ini anda \n telah menyetujui seluruh \n kebijakan kami");
		
		btnDaftar = new Button(shlDaftar, SWT.NONE);
		btnDaftar.setBounds(355, 328, 85, 26);
		btnDaftar.setText("DAFTAR");
		btnDaftar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String nama = inputNama.getText();
				String alamat = inputAlamat.getText();
				String telp = inputTelp.getText();
				String no_ktp = inputKTP.getText();
				String email = inputEmail.getText();
				String password = inputPassword.getText();
				try {
					prosesDaftar(nama,email,password,alamat,telp,no_ktp);
				} catch (NullPointerException e2) {
					MessageBox dialog = new MessageBox(shlDaftar, SWT.ICON_ERROR | SWT.OK);
		    		dialog.setMessage("Form tidak boleh kosong");
		    		dialog.open();
				}
			}
		});
		
		lblJudulDaftar = new Label(shlDaftar, SWT.NONE);
		lblJudulDaftar.setAlignment(SWT.CENTER);
		lblJudulDaftar.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblJudulDaftar.setBounds(36, 129, 148, 36);
		lblJudulDaftar.setText("DAFTAR");
		
		lblDeskripsiDaftar = new Label(shlDaftar, SWT.NONE);
		lblDeskripsiDaftar.setAlignment(SWT.CENTER);
		lblDeskripsiDaftar.setBounds(20, 161, 191, 21);
		lblDeskripsiDaftar.setText("Untuk mulai sewa menyewa");
		
		btnKembali = new Button(shlDaftar, SWT.NONE);
		btnKembali.setBounds(41, 188, 143, 26);
		btnKembali.setText("KEMBALI KE AWAL");
		btnKembali.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlDaftar.close();
				awal a = new awal();
				a.main(null);
			}
		});
		
		Label lblSeparator = new Label(shlDaftar, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparator.setText("Separator");
		lblSeparator.setBounds(233, 37, 1, 290);
	}
	
	public void prosesDaftar(String nama, String email, String password, String alamat, String telp, String no_ktp) {
		try{
			 Class.forName("com.mysql.cj.jdbc.Driver");  
			   Connection con=DriverManager.getConnection(  
			     "jdbc:mysql://localhost:3306/sewamobil","bayu","123qwe");
			   String sql = "insert into user_ values(NULL,?,?,?,?,?,?,NULL,NULL,NULL,NULL,NULL)";
				PreparedStatement prepare = con.prepareStatement(sql);
				
				prepare.setString(1, nama);
				prepare.setString(2, email);
				prepare.setString(3, password);
				prepare.setString(4, alamat);
				prepare.setString(5, telp);
				prepare.setString(6, no_ktp);
				if(nama != null && !nama.isEmpty() && email != null && !email.isEmpty() && password != null && !password.isEmpty() && alamat != null && !alamat.isEmpty() && telp != null && !telp.isEmpty() && no_ktp != null && !no_ktp.isEmpty()) {
					prepare.executeUpdate();
					MessageBox dialog1 = new MessageBox(shlDaftar, SWT.ICON_INFORMATION | SWT.OK);
		    		dialog1.setMessage("Berhasil! Silahkan Login");
		    		dialog1.open();
					prepare.close();
				} else {
					MessageBox dialog2 = new MessageBox(shlDaftar, SWT.ICON_ERROR | SWT.OK);
		    		dialog2.setMessage("Gagal! Silahkan coba lagi");
		    		dialog2.open();
				}
			}
			catch(SQLException | ClassNotFoundException ex)
			{
				MessageBox dialog1 = new MessageBox(shlDaftar, SWT.ICON_ERROR | SWT.OK);
				dialog1.setMessage("Email sudah terdaftar");
	    		dialog1.open();
			}
	}
}
