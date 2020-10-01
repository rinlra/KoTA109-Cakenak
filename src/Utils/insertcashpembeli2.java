package Utils;

import java.io.IOException;

import dbrepo.RepoAdmin;
import model.Admin;

public class insertcashpembeli2 {

	
	public static void main(String[] args) throws IOException {
		Admin admin = new Admin();
		RepoAdmin repoAdmin = new RepoAdmin();
		
		admin.setId("rowAdmin1");
		admin.setRevenue(0);
		repoAdmin.updateRevenueAdmin(admin);
	}
	
}
