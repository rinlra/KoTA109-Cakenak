package Utils;

import java.io.IOException;

import dbrepo.RepoAdmin;
import model.Admin;

public class UpdateAdmin {

	public static void main(String[] args) {
		
		RepoAdmin repoAdmin = new RepoAdmin();
		
		Admin admin = new Admin();
		
		admin.setId("rowAdmin0");
		admin.setEmail("admin1@mailtrap.io");
		admin.setFilegbrakun("akunadmindefault.png");
		admin.setNama("admin1");
		admin.setRevenue((double) 0);
		admin.setTipeakun("ROLE_ADMIN");
		admin.setUsername("admin1");
		admin.setPassword(SHA.encrypt("123"));
		try {
			repoAdmin.updateAdmin(admin);
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		
	}
	
}
