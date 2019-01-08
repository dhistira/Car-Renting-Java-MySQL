import java.util.HashMap;
import java.util.Map;

public class SessionSave {
	
	public String email;
	public String nama;
	public String id;
	
	public void saveSession(String email, String nama, String id) {
		this.email = email; 
		this.nama = nama;
		this.id = id;
	}
	
	public Map<String, String> getSession() {
		Map<String,String> datauser = new HashMap<>();
		datauser.put("id", this.id);
		datauser.put("nama", this.nama);
		datauser.put("email", this.email);
		return datauser;
	}

}
