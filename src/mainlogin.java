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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class mainlogin {
	protected Shell shellMainLogin;
	private static Text inputSearchHarga;
	private static daftar shellMainLoginDaftar = new daftar();
	private static awal awal = new awal();
	private String namauser;
	private String emailuser;
	private String iduser;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	
	public static void main(String[] args) {
		try {
			mainlogin window = new mainlogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void open(String namauser, String emailuser, String iduser) {
		this.namauser = namauser;
		this.emailuser = emailuser;
		this.iduser = iduser;
		Display display = Display.getDefault();
		createContents(namauser,emailuser,iduser);
		shellMainLogin.open();
		shellMainLogin.layout();
		while (!shellMainLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	protected void createContents(String namauser, String emailuser, String iduser) {
		shellMainLogin = new Shell(SWT.TITLE | SWT.CLOSE | SWT.BORDER);
		shellMainLogin.setSize(560, 440);
		shellMainLogin.setText("HALO "+namauser+"!");
		
		Label lblFilter = new Label(shellMainLogin, SWT.NONE);
		lblFilter.setBounds(387, 8, 69, 21);
		lblFilter.setText("FILTER");
		
		Label lblTipeMobil = new Label(shellMainLogin, SWT.NONE);
		lblTipeMobil.setBounds(297, 35, 108, 21);
		lblTipeMobil.setText("Tipe Mobil:");
		
		Combo inputTipeMobil = new Combo(shellMainLogin, SWT.NONE);
		inputTipeMobil.setBounds(297, 59, 225, 25);
		inputTipeMobil.add("Mobil Keluarga", 0);
		inputTipeMobil.add("Mobil Pickup", 1);
		inputTipeMobil.add("Mobil Mini van", 2);
		inputTipeMobil.add("Motor", 3);
		inputTipeMobil.select(0);
		
		Label lblHarga = new Label(shellMainLogin, SWT.NONE);
		lblHarga.setBounds(297, 93, 69, 21);
		lblHarga.setText("Harga");
		
		inputSearchHarga = new Text(shellMainLogin, SWT.BORDER);
		inputSearchHarga.setBounds(297, 115, 225, 24);
		inputSearchHarga.setMessage("Contoh: 300000");
		
		Button btnCari = new Button(shellMainLogin, SWT.FLAT);
		btnCari.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String tipemobil = inputTipeMobil.getText();
				String hargamobil = inputSearchHarga.getText();
				hasilSearch search = new hasilSearch();
				shellMainLogin.close();
				search.open(namauser, emailuser, iduser, tipemobil, hargamobil);
			}
		});
		btnCari.setBounds(371, 160, 85, 26);
		btnCari.setText("Cari");
		
		Label separatorLogin = new Label(shellMainLogin, SWT.SEPARATOR | SWT.HORIZONTAL);
		separatorLogin.setText("Login");
		separatorLogin.setBounds(297, 204, 225, 1);
		
		Label lblMenu = new Label(shellMainLogin, SWT.NONE);
		lblMenu.setAlignment(SWT.CENTER);
		lblMenu.setBounds(297, 215, 225, 21);
		lblMenu.setText("Halo "+namauser+"!");
		
		Button btnLogout = new Button(shellMainLogin, SWT.FLAT | SWT.PASSWORD);
		btnLogout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellMainLogin.close();
				awal.main(null);
			}
		});
		btnLogout.setBounds(437, 305, 85, 26);
		btnLogout.setText("LOGOUT");
		
		Button btnAkunSaya = new Button(shellMainLogin, SWT.FLAT);
		btnAkunSaya.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellMainLogin.close();
				akunsaya akunsaya = new akunsaya();
				akunsaya.open(namauser,emailuser,iduser);
				
			}
		});
		btnAkunSaya.setBounds(297, 305, 134, 26);
		btnAkunSaya.setText("AKUN SAYA");
		
		Browser browser = new Browser(shellMainLogin, SWT.NONE);
		browser.setBounds(10, 8, 268, 375);
		final String dir = System.getProperty("user.dir");
		String urlnya = dir+"/dialog.html";
		browser.setUrl(urlnya);
		browser.addProgressListener(new ProgressListener() {
			public void completed(ProgressEvent event) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewamobil", "bayu",
							"123qwe");
					String sql = "select count(*) from kendaraan WHERE STATUS = '0'";
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
					String sql = "select * from kendaraan WHERE STATUS = '0' ORDER BY PLAT_NO DESC";
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
								+ "<br>Kapasitas: " + kapasitasmobil + " Orang<br><a href = \"?plat=order-" + platmobil
								+ "-"+hargamobil+"\">Pesan</a><hr>');");
					}
				} catch (SQLException | ClassNotFoundException e) {

				}
			}

			@Override
			public void changed(ProgressEvent arg0) {
				// TODO Auto-generated method stub
				String url = browser.getUrl();
				Integer generate = url.lastIndexOf("=") + 1;
				String generated = url.substring(generate);
				if (generated.contains("order")) {
					openSingleSewa(namauser,emailuser,iduser,generated);
				}
			}
		});
		
		Button btnMenyewakan = new Button(shellMainLogin, SWT.NONE);
		btnMenyewakan.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellMainLogin.close();
				tambahSewa tambahsewa = new tambahSewa();
				tambahsewa.open(namauser, emailuser, iduser);
			}
		});
		btnMenyewakan.setBounds(297, 241, 225, 26);
		SessionSave session = new SessionSave();
		
		btnMenyewakan.setText("Saya Ingin Menyewakan");
		
		Button btnSewaSaya = new Button(shellMainLogin, SWT.NONE);
		btnSewaSaya.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellMainLogin.close();
				sewaSaya sewasaya = new sewaSaya();
				sewasaya.open(namauser,emailuser,iduser);
			}
		});
		btnSewaSaya.setBounds(437, 273, 85, 26);
		btnSewaSaya.setText("SEWA SAYA");
		
		Button btnMobilSaya = new Button(shellMainLogin, SWT.NONE);
		btnMobilSaya.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellMainLogin.close();
				mobilSaya mobilsaya = new mobilSaya();
				mobilsaya.open(namauser,emailuser,iduser);
				
			}
		});
		btnMobilSaya.setBounds(297, 273, 134, 26);
		btnMobilSaya.setText("MOBIL SAYA");
		System.out.println(urlnya);
	}
	
	public void prosesLogin(String email, String password) throws ClassNotFoundException{
		try{
			 Class.forName("com.mysql.cj.jdbc.Driver");  
			   Connection con=DriverManager.getConnection(  
			     "jdbc:mysql://localhost:3306/sewamobil","bayu","123qwe");
		} catch(SQLException  e) {
			
		}
	}
	
	public void openSingleSewa(String namauser, String emailuser, String iduser, String idkendaraan) {
		shellMainLogin.close();
		singleSewa sewa = new singleSewa();
		sewa.open(namauser,emailuser,iduser,idkendaraan);
	}
}
