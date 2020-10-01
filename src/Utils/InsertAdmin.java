package Utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import dbrepo.RepoAdmin;
import model.Admin;

public class InsertAdmin {

	public static void main(String[] args) {
		
		RepoAdmin repoAdmin = new RepoAdmin();
		
		Admin admin = new Admin();
		
		admin.setEmail("admin1@mailtrap.io");
		admin.setFilegbrakun("akunadmindefault.png");
		admin.setNama("admin1");
		admin.setRevenue((double) 0);
		admin.setTipeakun("ROLE_ADMIN");
		admin.setUsername("admin1");
		admin.setPassword(SHA.encrypt("123"));
		try {
			repoAdmin.insertDataAdmin(admin);
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		
	}

}
