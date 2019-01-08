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

public class sewaSaya {
	protected Shell shellSewaSaya;
	private static Text inputSearchHarga;
	private static daftar shellMainLoginDaftar = new daftar();
	private static awal awal = new awal();
	private String namauser;
	private String emailuser;
	private String iduser;

	/**
	 * Launch the application.
	 * 
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
		createContents(namauser, emailuser, iduser);
		shellSewaSaya.open();
		shellSewaSaya.layout();
		while (!shellSewaSaya.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents(String namauser, String emailuser, String iduser) {
		shellSewaSaya = new Shell(SWT.TITLE | SWT.CLOSE | SWT.BORDER);
		shellSewaSaya.setSize(560, 440);
		shellSewaSaya.setText("LIST SEWA - " + namauser);

		Label lblFilter = new Label(shellSewaSaya, SWT.NONE);
		lblFilter.setBounds(387, 8, 69, 21);
		lblFilter.setText("FILTER");

		Label lblTipeMobil = new Label(shellSewaSaya, SWT.NONE);
		lblTipeMobil.setBounds(297, 35, 108, 21);
		lblTipeMobil.setText("Tipe Mobil:");

		Combo inputTipeMobil = new Combo(shellSewaSaya, SWT.NONE);
		inputTipeMobil.setBounds(297, 59, 225, 25);
		inputTipeMobil.add("Mobil Keluarga", 0);
		inputTipeMobil.add("Mobil Pickup", 1);
		inputTipeMobil.add("Mobil Mini van", 2);
		inputTipeMobil.add("Motor", 3);
		inputTipeMobil.select(0);
		
		Label lblHarga = new Label(shellSewaSaya, SWT.NONE);
		lblHarga.setBounds(297, 93, 69, 21);
		lblHarga.setText("Harga");
		
		inputSearchHarga = new Text(shellSewaSaya, SWT.BORDER);
		inputSearchHarga.setBounds(297, 115, 225, 24);
		inputSearchHarga.setMessage("Contoh: 300000");
		
		Button btnCari = new Button(shellSewaSaya, SWT.FLAT);
		btnCari.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String tipemobil = inputTipeMobil.getText();
				String hargamobil = inputSearchHarga.getText();
				hasilSearch search = new hasilSearch();
				shellSewaSaya.close();
				search.open(namauser, emailuser, iduser, tipemobil, hargamobil);
			}
		});
		btnCari.setBounds(371, 160, 85, 26);
		btnCari.setText("Cari");

		Label separatorLogin = new Label(shellSewaSaya, SWT.SEPARATOR | SWT.HORIZONTAL);
		separatorLogin.setText("Login");
		separatorLogin.setBounds(297, 204, 225, 1);

		Label lblMenu = new Label(shellSewaSaya, SWT.NONE);
		lblMenu.setAlignment(SWT.CENTER);
		lblMenu.setBounds(297, 215, 225, 21);
		lblMenu.setText("Halo " + namauser + "!");

		Button btnLogout = new Button(shellSewaSaya, SWT.FLAT | SWT.PASSWORD);
		btnLogout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellSewaSaya.close();
				awal.main(null);
			}
		});
		btnLogout.setBounds(437, 305, 85, 26);
		btnLogout.setText("LOGOUT");

		Button btnAkunSaya = new Button(shellSewaSaya, SWT.FLAT);
		btnAkunSaya.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellSewaSaya.close();
				akunsaya akunsaya = new akunsaya();
				akunsaya.open(namauser, emailuser, iduser);

			}
		});
		btnAkunSaya.setBounds(297, 305, 134, 26);
		btnAkunSaya.setText("AKUN SAYA");

		Browser browser = new Browser(shellSewaSaya, SWT.NONE);
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
					String sql = "select count(*) from sewa WHERE ID_PENYEWA = '"+iduser+"'";
					PreparedStatement prepare = con.prepareStatement(sql);
					ResultSet rs = prepare.executeQuery(sql);
					while(rs.next()) {
						browser.execute("$('#infomobil').append('<b>Total Sewa Saya:</b> "+rs.getString(1)+"');");
					}
				}catch(SQLException|ClassNotFoundException e) {
					
				}
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewamobil", "bayu",
							"123qwe");
					String sql = "select sewa.tgl_pinjam,sewa.tgl_kembali,sewa.total_biaya_sewa,kendaraan.NAMA,kendaraan.WARNA,kendaraan.PLAT_NO from sewa JOIN kendaraan ON sewa.ID_KENDARAAN = kendaraan.PLAT_NO WHERE sewa.ID_PENYEWA = '"
							+ iduser + "'";
					PreparedStatement prepare = con.prepareStatement(sql);
					ResultSet rs = prepare.executeQuery(sql);
					while (rs.next()) {
						String tgl_pinjam = rs.getString(1);
						String tgl_kembali = rs.getString(2);
						String total_biaya = rs.getString(3);
						String namamobil = rs.getString(4);
						String warnamobil = rs.getString(5);
						String platmobil = rs.getString(6);

						String[] tgl_pinjamz = tgl_pinjam.split("/");
						String haripinjam = tgl_pinjamz[0];
						String[] tgl_kembaliz = tgl_kembali.split("/");
						String harikembali = tgl_kembaliz[0];
						int hari1 = Integer.parseInt(haripinjam);
						int hari2 = Integer.parseInt(harikembali);
						int durasi = hari2 - hari1;
						if(durasi == 0) {
							durasi = 1;
						}
						int subtotalbiaya = Integer.parseInt(total_biaya) * durasi;

						// Execute JavaScript in the browser
						browser.execute("$('#wrapper').append('<b>" + namamobil + " "+warnamobil+"</b><br>Lama Sewa: " + durasi + "<br>Total Biaya: " + subtotalbiaya
								+ "<br><a href = \"?plat=hapus-" + platmobil + "\">HAPUS</a><hr>');");
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
					openSingleSewa(namauser, emailuser, iduser, generated);
				} else if (generated.contains("edit")) {
					openEditMobil(namauser, emailuser, iduser, generated);
				} else if (generated.contains("hapus")) {
					hapusMobil(generated);
				}
			}
		});

		Button btnMenyewakan = new Button(shellSewaSaya, SWT.NONE);
		btnMenyewakan.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellSewaSaya.close();
				tambahSewa tambahsewa = new tambahSewa();
				tambahsewa.open(namauser, emailuser, iduser);
			}
		});
		btnMenyewakan.setBounds(297, 241, 225, 26);
		SessionSave session = new SessionSave();

		btnMenyewakan.setText("Saya Ingin Menyewakan");

		Button btnSewaSaya = new Button(shellSewaSaya, SWT.NONE);
		btnSewaSaya.setBounds(437, 273, 85, 26);
		btnSewaSaya.setText("SEWA SAYA");

		Button btnMobilSaya = new Button(shellSewaSaya, SWT.NONE);
		btnMobilSaya.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellSewaSaya.close();
				mainlogin ml = new mainlogin();
				ml.open(namauser, emailuser, iduser);
			}
		});
		btnMobilSaya.setBounds(297, 273, 134, 26);
		btnMobilSaya.setText("KEMBALI");
		System.out.println(urlnya);
	}

	public void prosesLogin(String email, String password) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewamobil", "bayu", "123qwe");
		} catch (SQLException e) {

		}
	}

	public void openSingleSewa(String namauser, String emailuser, String iduser, String idkendaraan) {
		shellSewaSaya.close();
		singleSewa sewa = new singleSewa();
		sewa.open(namauser, emailuser, iduser, idkendaraan);
	}

	public void openEditMobil(String namauser, String emailuser, String iduser, String generatedMobil) {
		shellSewaSaya.close();
		editMobil edit = new editMobil();
		edit.open(namauser, emailuser, iduser, generatedMobil);
	}

	public void hapusMobil(String generatedMobil) {
		String[] pecahMobil = generatedMobil.split("-");
		String platnomor = pecahMobil[1];
		MessageBox messageBox = new MessageBox(shellSewaSaya, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Apakah anda ingin menghapus " + platnomor + "?");
		messageBox.setText("HAPUS " + platnomor + "");
		int response = messageBox.open();
		if (response == SWT.YES) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewamobil", "bayu", "123qwe");
				String sql = "DELETE FROM sewa WHERE ID_KENDARAAN = '" + platnomor + "' AND ID_PENYEWA = '" + iduser
						+ "'";
				PreparedStatement prepare = con.prepareStatement(sql);
				prepare.executeUpdate();
				MessageBox dialog1 = new MessageBox(shellSewaSaya, SWT.ICON_INFORMATION | SWT.OK);
				dialog1.setMessage("Berhasil! Data berhasil dihapus");
				dialog1.open();
				prepare.close();
				shellSewaSaya.close();
				mainlogin ml = new mainlogin();
				ml.open(namauser, emailuser, iduser);
			} catch (SQLException | ClassNotFoundException ex) {
				MessageBox dialog1 = new MessageBox(shellSewaSaya, SWT.ICON_ERROR | SWT.OK);
				dialog1.setMessage("Gagal! Silahkan coba lagi!");
				dialog1.open();
			}
		}
	}
}
