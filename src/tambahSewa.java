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

public class tambahSewa {

	protected Shell shlTambahSewa;
	private Button btnSimpan;
	private Label lblAkunSaya;
	private Label lblTambahSewa;
	private Button btnKembali;
	private static userClass user = new userClass();
	
	//DATABASE KONEKSI
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
		shlTambahSewa.open();
		shlTambahSewa.layout();
		while (!shlTambahSewa.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String namauser, String emailuser, String iduser) {
		shlTambahSewa = new Shell(SWT.TITLE | SWT.CLOSE | SWT.BORDER);
		shlTambahSewa.setSize(560, 440);
		shlTambahSewa.setText("TAMBAH SEWA");
		
		inputNamaMobil = new Text(shlTambahSewa, SWT.BORDER);
		inputNamaMobil.setBounds(251, 53, 282, 24);
		inputNamaMobil.setMessage("Nama Mobil, Misal: Avanza");
		
		inputPlat = new Text(shlTambahSewa, SWT.BORDER);
		inputPlat.setBounds(251, 85, 282, 24);
		inputPlat.setMessage("Plat Nomor, Misal: A4937CG");
		
		inputHarga = new Text(shlTambahSewa, SWT.BORDER);
		inputHarga.setBounds(251, 115, 282, 24);
		inputHarga.setMessage("Harga perhari, misal: 300000");
		
		inputKapasitas = new Text(shlTambahSewa, SWT.BORDER);
		inputKapasitas.setBounds(251, 145, 143, 24);
		inputKapasitas.setMessage("Kapasitas");
		
		inputTahun = new Text(shlTambahSewa, SWT.BORDER);
		inputTahun.setBounds(400, 145, 133, 24);
		inputTahun.setMessage("Tahun Keluar");
		
		inputJenis = new Combo(shlTambahSewa, SWT.NONE);
		inputJenis.setBounds(251, 176, 283, 25);
		inputJenis.add("Mobil Keluarga", 0);
		inputJenis.add("Mobil Pickup", 1);
		inputJenis.add("Mobil Mini van", 2);
		inputJenis.add("Motor", 3);
		inputJenis.select(0);
		
		inputWarna = new Text(shlTambahSewa, SWT.BORDER);
		inputWarna.setMessage("Warna Mobil");
		inputWarna.setBounds(251, 207, 282, 24);
		
		btnSimpan = new Button(shlTambahSewa, SWT.NONE);
		btnSimpan.setBounds(342, 245, 85, 26);
		btnSimpan.setText("SIMPAN");
		btnSimpan.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String nama = inputNamaMobil.getText();
				String plat = inputPlat.getText();
				String harga = inputHarga.getText();
				String kapasitas = inputKapasitas.getText();
				String tahun = inputTahun.getText();
				String tipe = inputJenis.getText();
				String warna = inputWarna.getText();
				
				try {
					prosesInput(namauser,emailuser,nama,plat,harga,kapasitas,tahun,tipe,warna,iduser);
				} catch (NullPointerException e2) {
					MessageBox dialog = new MessageBox(shlTambahSewa, SWT.ICON_ERROR | SWT.OK);
		    		dialog.setMessage("Form tidak boleh kosong");
		    		dialog.open();
				}
			}
		});
		
		lblTambahSewa = new Label(shlTambahSewa, SWT.NONE);
		lblTambahSewa.setAlignment(SWT.CENTER);
		lblTambahSewa.setBounds(1, 161, 226, 21);
		lblTambahSewa.setText("Formulir Tambah Sewa");
		
		btnKembali = new Button(shlTambahSewa, SWT.NONE);
		btnKembali.setBounds(41, 188, 143, 26);
		btnKembali.setText("KEMBALI");
		btnKembali.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlTambahSewa.close();
				mainlogin ml = new mainlogin();
				ml.open(namauser,emailuser,iduser);
			}
		});
		
		Label lblSeparator = new Label(shlTambahSewa, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparator.setText("Separator");
		lblSeparator.setBounds(233, 37, 1, 290);
		
		lblAkunSaya = new Label(shlTambahSewa, SWT.NONE);
		lblAkunSaya.setAlignment(SWT.CENTER);
		lblAkunSaya.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblAkunSaya.setBounds(0, 129, 227, 36);
		lblAkunSaya.setText("TAMBAH SEWA");
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
	
	public void prosesInput(String namauser, String emailuser, String nama, String plat, String harga, String kapasitas, String tahun, String tipe, String warna, String iduser) {
		try{
			 Class.forName("com.mysql.cj.jdbc.Driver");  
			   Connection con=DriverManager.getConnection(  
			     "jdbc:mysql://localhost:3306/sewamobil","bayu","123qwe");
			   String sql = "insert into kendaraan values(?,?,?,?,?,?,?,NULL,?,'0')";
				PreparedStatement prepare = con.prepareStatement(sql);
				
				prepare.setString(1, plat);
				prepare.setString(2, nama);
				prepare.setString(3, harga);
				prepare.setString(4, kapasitas);
				prepare.setString(5, tahun);
				prepare.setString(6, tipe);
				prepare.setString(7, warna);
				prepare.setString(8, iduser);
				if(plat != null && !plat.isEmpty() && harga != null && !harga.isEmpty() && kapasitas != null && !kapasitas.isEmpty() && tahun != null && !tahun.isEmpty() && tipe != null & !tipe.isEmpty() && warna != null && !warna.isEmpty()) {
					prepare.executeUpdate();
					MessageBox dialog1 = new MessageBox(shlTambahSewa, SWT.ICON_INFORMATION | SWT.OK);
		    		dialog1.setMessage("Berhasil! data berhasil ditambah");
		    		dialog1.open();
		    		shlTambahSewa.close();
		    		mainlogin ml = new mainlogin();
					ml.open(namauser, emailuser, iduser);
					prepare.close();
				} else {
					MessageBox dialog2 = new MessageBox(shlTambahSewa, SWT.ICON_ERROR | SWT.OK);
		    		dialog2.setMessage("Ada field yang kosong!");
		    		dialog2.open();
				}
			}
			catch(SQLException | ClassNotFoundException ex)
			{
				MessageBox dialog1 = new MessageBox(shlTambahSewa, SWT.ICON_ERROR | SWT.OK);
				dialog1.setMessage("Gagal! Silahkan coba lagi!");
	    		dialog1.open();
			}
	}
}
