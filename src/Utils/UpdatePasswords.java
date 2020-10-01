package Utils;

import java.io.IOException;
import java.util.ArrayList;

import dbrepo.RepoPenjual;
import model.Penjual;

public class UpdatePasswords {

	public static void main(String args[]) throws IOException {
		
		ArrayList<Penjual> penjualjual = new ArrayList<Penjual>();
		RepoPenjual repoPenjual = new RepoPenjual();
		
		penjualjual = repoPenjual.getAllPenjual();
		
		penjualjual.forEach(penjual -> {
			penjual.setPassword("123");
			penjual.setTipeakun("ROLE_SELLER");
			repoPenjual.updatePenjual(penjual);
		});
	}
	
}
