import java.sql.Statement;
import java.time.Period;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class awal {
	private static Text inputSearchHarga;
	private static Text inputEmail;
	private static Text inputPassword;
	private static daftar shellDaftar = new daftar();
	private static mainlogin shellMainLogin = new mainlogin();
	private static userClass user = new userClass();
	private ResultSet a;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display,SWT.TITLE | SWT.CLOSE | SWT.BORDER);
		shell.setSize(560, 440);
		shell.setText("Rental Mobil");

		Label lblFilter = new Label(shell, SWT.NONE);
		lblFilter.setBounds(387, 8, 69, 21);
		lblFilter.setText("FILTER");

		Label lblTipeMobil = new Label(shell, SWT.NONE);
		lblTipeMobil.setBounds(297, 35, 108, 21);
		lblTipeMobil.setText("Tipe Mobil:");

		Combo inputTipeMobil = new Combo(shell, SWT.NONE);
		inputTipeMobil.setBounds(297, 59, 225, 25);
		inputTipeMobil.add("Mobil Keluarga", 0);
		inputTipeMobil.add("Mobil Pickup", 1);
		inputTipeMobil.add("Mobil Mini van", 2);
		inputTipeMobil.add("Motor", 3);
		inputTipeMobil.select(0);

		Label lblHarga = new Label(shell, SWT.NONE);
		lblHarga.setBounds(297, 93, 69, 21);
		lblHarga.setText("Harga");

		inputSearchHarga = new Text(shell, SWT.BORDER);
		inputSearchHarga.setBounds(297, 115, 225, 24);
		inputSearchHarga.setMessage("Contoh: 300000");

		Button btnCari = new Button(shell, SWT.FLAT);
		btnCari.setBounds(371, 160, 85, 26);
		btnCari.setText("Cari");
		btnCari.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
	    		dialog.setMessage("Anda Harus Login untuk Melakukan Pencarian");
	    		dialog.open();
			}
		});

		Label lblLogin = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblLogin.setText("Login");
		lblLogin.setBounds(297, 204, 237, 1);

		Label lblLogin_1 = new Label(shell, SWT.NONE);
		lblLogin_1.setBounds(387, 215, 69, 21);
		lblLogin_1.setText("LOGIN");

		Label lblEmail = new Label(shell, SWT.NONE);
		lblEmail.setBounds(297, 249, 69, 21);
		lblEmail.setText("Email:");

		inputEmail = new Text(shell, SWT.BORDER);
		inputEmail.setBounds(371, 246, 151, 24);
		inputEmail.setMessage("yourname@example.com");

		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setBounds(297, 276, 69, 21);
		lblPassword.setText("Password:");

		inputPassword = new Text(shell, SWT.BORDER);
		inputPassword.setBounds(371, 276, 151, 24);
		inputPassword.setMessage("******");

		Button btnLogin = new Button(shell, SWT.FLAT | SWT.PASSWORD);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String email = inputEmail.getText();
				String password = inputPassword.getText();
				if (user.prosesLogin(email, password) == true) {
					String cd = user.getUserData(email);
					String[] oke = cd.split("-");
					String namauser = oke[0];
					String iduser = oke[1];

					mainlogin ml = new mainlogin();
					shell.close();
					ml.open(namauser, email, iduser);
				}
			}
		});
		btnLogin.setBounds(437, 316, 85, 26);
		btnLogin.setText("LOGIN");

		Button btnBuatAkun = new Button(shell, SWT.FLAT);
		btnBuatAkun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				shellDaftar.open();
			}
		});
		btnBuatAkun.setBounds(297, 316, 134, 26);
		btnBuatAkun.setText("BUAT AKUN");

		Browser browser = new Browser(shell, SWT.NONE);
		browser.setBounds(10, 8, 268, 375);
		final String dir = System.getProperty("user.dir");
		String urlnya = dir + "/dialog.html";
		browser.setUrl(urlnya);
		browser.addProgressListener(new ProgressListener() {
			public void completed(ProgressEvent event) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewamobil", "bayu",
							"123qwe");
					String sql = "select count(*) from kendaraan";
					PreparedStatement prepare = con.prepareStatement(sql);
					ResultSet rs = prepare.executeQuery(sql);
					while(rs.next()) {
						browser.execute("$('#infomobil').append('<b>Total Mobil:</b> "+rs.getString(1)+"');");
					}
				}catch(SQLException|ClassNotFoundException e) {
					
				}
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewamobil", "bayu",
							"123qwe");
					String sql = "select * from kendaraan ORDER BY PLAT_NO DESC";
					PreparedStatement prepare = con.prepareStatement(sql);
					ResultSet rs = prepare.executeQuery(sql);
					
					while (rs.next()) {
						String platmobil = rs.getString(1);
						String namamobil = rs.getString(2);
						String hargamobil = rs.getString(3);
						String kapasitasmobil = rs.getString(4);
						String warnamobil = rs.getString(7);

						// Execute JavaScript in the browser
						browser.execute("$('#wrapper').append('<b>" + namamobil + " "+warnamobil+"</b><br>Rp." + hargamobil
								+ "<br>Kapasitas: " + kapasitasmobil + " Orang<br><a>Anda Harus Login untuk Memesan</a><hr>');");
					}
				} catch (SQLException | ClassNotFoundException e) {

				}
			}

			@Override
			public void changed(ProgressEvent arg0) {
				// TODO Auto-generated method stub
				String generated = browser.getUrl().substring(browser.getUrl().lastIndexOf("=") + 1);
				if (generated.contains("edit"))
					System.out.println(generated);
			}
		});

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
