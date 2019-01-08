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
import org.eclipse.swt.widgets.Combo;

public class editMobil {

	protected Shell shlEditMobil;
	private Button btnSimpan;
	private Label lblEditMobil;
	private Label lblTambahSewa;
	private Button btnKembali;
	private static userClass user = new userClass();

	// DATABASE KONEKSI
	private koneksi connect = new koneksi();
	private Statement stmt = null;
	private ResultSet rs = null;
	private Text inputPlat;
	private Text inputHarga;
	private Text inputKapasitas;
	private Text inputTahun;
	private Combo inputJenis;
	private Text inputWarna;
	private Text inputNamaMobil;
	private String platmobil;
	private String hargamobil;
	private String warnamobil;

	/**
	 * Launch the application.
	 * 
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
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open(String namauser, String emailuser, String iduser, String generatedMobil) {
		Display display = Display.getDefault();
		createContents(namauser, emailuser, iduser, generatedMobil);
		shlEditMobil.open();
		shlEditMobil.layout();
		while (!shlEditMobil.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String namauser, String emailuser, String iduser, String generatedMobil) {
		shlEditMobil = new Shell(SWT.TITLE | SWT.CLOSE | SWT.BORDER);
		shlEditMobil.setSize(560, 440);
		shlEditMobil.setText("EDIT MOBIL");

		// PECAH GENERATED MOBIL
		String[] arrayMobil = generatedMobil.split("-");
		this.platmobil = arrayMobil[1];
		this.hargamobil = arrayMobil[2];
		this.warnamobil = arrayMobil[3];

		inputPlat = new Text(shlEditMobil, SWT.BORDER);
		inputPlat.setBounds(251, 85, 282, 24);
		inputPlat.setMessage("Plat Nomor, Misal: A4937CG");
		inputPlat.setText(platmobil);
		inputPlat.setEnabled(false);

		inputHarga = new Text(shlEditMobil, SWT.BORDER);
		inputHarga.setBounds(251, 115, 282, 24);
		inputHarga.setMessage("Harga perhari, misal: 300000");
		inputHarga.setText(hargamobil);

		inputWarna = new Text(shlEditMobil, SWT.BORDER);
		inputWarna.setMessage("Warna Mobil");
		inputWarna.setBounds(251, 207, 282, 24);
		inputWarna.setText(warnamobil);

		btnSimpan = new Button(shlEditMobil, SWT.NONE);
		btnSimpan.setBounds(342, 245, 85, 26);
		btnSimpan.setText("SIMPAN");
		btnSimpan.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String harga = inputHarga.getText();
				String warna = inputWarna.getText();
				String platmobil2 = platmobil;

				try {
					prosesEdit(harga, warna, platmobil2);
				} catch (NullPointerException e2) {
					MessageBox dialog = new MessageBox(shlEditMobil, SWT.ICON_ERROR | SWT.OK);
					dialog.setMessage("Form tidak boleh kosong");
					dialog.open();
				}
			}
		});

		lblTambahSewa = new Label(shlEditMobil, SWT.NONE);
		lblTambahSewa.setAlignment(SWT.CENTER);
		lblTambahSewa.setBounds(1, 161, 226, 21);
		lblTambahSewa.setText("Formulir Edit Mobil");

		btnKembali = new Button(shlEditMobil, SWT.NONE);
		btnKembali.setBounds(41, 188, 143, 26);
		btnKembali.setText("KEMBALI");
		btnKembali.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlEditMobil.close();
				mainlogin ml = new mainlogin();
				ml.open(namauser, emailuser, iduser);
			}
		});

		Label lblSeparator = new Label(shlEditMobil, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparator.setText("Separator");
		lblSeparator.setBounds(233, 37, 1, 290);

		lblEditMobil = new Label(shlEditMobil, SWT.NONE);
		lblEditMobil.setAlignment(SWT.CENTER);
		lblEditMobil.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblEditMobil.setBounds(0, 129, 227, 36);
		lblEditMobil.setText("EDIT MOBIL");
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

	public void prosesEdit(String harga, String warna, String platmobil) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewamobil", "bayu", "123qwe");
			String sql = "UPDATE kendaraan SET HARGA ='"+harga+"', WARNA = '"+warna+"' WHERE PLAT_NO = '"+platmobil+"'";
			PreparedStatement prepare = con.prepareStatement(sql);
			if (!harga.isEmpty() && !warna.isEmpty()) {
				prepare.executeUpdate();
				MessageBox dialog1 = new MessageBox(shlEditMobil, SWT.ICON_INFORMATION | SWT.OK);
				dialog1.setMessage("Berhasil! data berhasil diubah");
				dialog1.open();
				prepare.close();
			} else {
				MessageBox dialog2 = new MessageBox(shlEditMobil, SWT.ICON_ERROR | SWT.OK);
				dialog2.setMessage("Ada field yang kosong!");
				dialog2.open();
			}
		} catch (SQLException | ClassNotFoundException ex) {
			MessageBox dialog1 = new MessageBox(shlEditMobil, SWT.ICON_ERROR | SWT.OK);
			dialog1.setMessage("Gagal! Silahkan coba lagi!");
			dialog1.open();
		}
	}
}
