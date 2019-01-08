import java.sql.Statement;
import java.util.Date;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;

public class singleSewa {

	protected Shell shellSingleSewa;
	private Button btnSimpan;
	private Label lblSewaMobil;
	private Label lblTambahSewa;
	private Button btnKembali;
	private static userClass user = new userClass();

	// DATABASE KONEKSI
	private koneksi connect = new koneksi();
	private Statement stmt = null;
	private ResultSet rs = null;
	private int hargamobil;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			singleSewa window = new singleSewa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open(String namauser, String emailuser, String iduser, String idkendaraan) {
		Display display = Display.getDefault();
		createContents(namauser, emailuser, iduser, idkendaraan);
		shellSingleSewa.open();
		shellSingleSewa.layout();
		while (!shellSingleSewa.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String namauser, String emailuser, String iduser, String idkendaraan) {
		shellSingleSewa = new Shell();
		shellSingleSewa.setSize(560, 440);
		shellSingleSewa.setText("SEWA MOBIL");

		lblTambahSewa = new Label(shellSingleSewa, SWT.NONE);
		lblTambahSewa.setAlignment(SWT.CENTER);
		lblTambahSewa.setBounds(1, 161, 226, 21);
		lblTambahSewa.setText("Formulir Sewa Mobil");

		btnKembali = new Button(shellSingleSewa, SWT.NONE);
		btnKembali.setBounds(41, 188, 143, 26);
		btnKembali.setText("KEMBALI");
		btnKembali.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellSingleSewa.close();
				mainlogin ml = new mainlogin();
				ml.open(namauser, emailuser, iduser);
			}
		});

		Label lblSeparator = new Label(shellSingleSewa, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparator.setText("Separator");
		lblSeparator.setBounds(233, 37, 1, 290);

		lblSewaMobil = new Label(shellSingleSewa, SWT.NONE);
		lblSewaMobil.setAlignment(SWT.CENTER);
		lblSewaMobil.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblSewaMobil.setBounds(0, 129, 227, 36);
		lblSewaMobil.setText("SEWA MOBIL");

		Label lblTanggalPinjam = new Label(shellSingleSewa, SWT.NONE);
		lblTanggalPinjam.setBounds(257, 119, 107, 21);
		lblTanggalPinjam.setText("Tanggal Pinjam");

		DateTime inputTanggalPinjam = new DateTime(shellSingleSewa, SWT.BORDER);
		inputTanggalPinjam.setBounds(257, 146, 100, 25);

		Label lblTanggalKembali = new Label(shellSingleSewa, SWT.NONE);
		lblTanggalKembali.setBounds(395, 119, 121, 21);
		lblTanggalKembali.setText("Tanggal Kembali");

		DateTime inputTanggalKembali = new DateTime(shellSingleSewa, SWT.BORDER);
		inputTanggalKembali.setBounds(395, 146, 100, 25);

		Combo inputPembayaran = new Combo(shellSingleSewa, SWT.NONE);
		inputPembayaran.setBounds(257, 204, 147, 25);
		inputPembayaran.add("Online", 0);
		inputPembayaran.add("Offline", 1);
		inputPembayaran.select(0);

		Label lblPembayaran = new Label(shellSingleSewa, SWT.NONE);
		lblPembayaran.setText("Pembayaran");
		lblPembayaran.setBounds(257, 177, 138, 21);

		btnSimpan = new Button(shellSingleSewa, SWT.NONE);
		btnSimpan.setBounds(410, 203, 85, 26);
		btnSimpan.setText("SIMPAN");
		btnSimpan.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String penyewa = iduser;
				int date_pinjam = inputTanggalPinjam.getDay();
				int bln_pinjam = inputTanggalPinjam.getMonth();
				int tahun_pinjam = inputTanggalPinjam.getYear();
				String tgl_pinjam = date_pinjam + "/" + bln_pinjam + "/" + tahun_pinjam;
				int date_kembali = inputTanggalKembali.getDay();
				int bln_kembali = inputTanggalKembali.getMonth();
				int tahun_kembali = inputTanggalKembali.getYear();
				String tgl_kembali = date_kembali + "/" + bln_kembali + "/" + tahun_kembali;
				String[] idkendaraanz = idkendaraan.split("-");
				String platno = idkendaraanz[1];
				String hargadaily = idkendaraanz[2];
				Integer metodebayar = inputPembayaran.getSelectionIndex();
				try {
					prosesSewa(platno, hargadaily, penyewa, tgl_pinjam, tgl_kembali, metodebayar);
				} catch (NullPointerException e2) {
					MessageBox dialog = new MessageBox(shellSingleSewa, SWT.ICON_ERROR | SWT.OK);
					dialog.setMessage("Terjadi Kesalahan");
					dialog.open();
				}
			}
		});
	}

	public String userData(String email, String ask) {
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

	public void prosesSewa(String idkendaraan, String hargadaily, String penyewa, String tgl_pinjam,
			String tgl_kembali, Integer metodebayar) {
		String idkendaraanz = idkendaraan;
		String penyewaz = penyewa;

		Shell shell = new Shell();
		int harganya = this.hargamobil;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewamobil", "bayu", "123qwe");
			String sql = "INSERT INTO sewa VALUES(NULL,'" + idkendaraanz + "','" + penyewaz + "','1','" + tgl_pinjam
					+ "','" + tgl_kembali + "'," + hargadaily + ","+metodebayar+")";
			PreparedStatement prepare = con.prepareStatement(sql);

			prepare.executeUpdate();
			MessageBox dialog1 = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
			dialog1.setMessage("Berhasil! Sewa anda telah diterima!");
			dialog1.open();
			prepare.close();
		} catch (SQLException | ClassNotFoundException ex) {
			MessageBox dialog1 = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			dialog1.setMessage("Terjadi Kesalahan");
			dialog1.open();
		}
	}
}
