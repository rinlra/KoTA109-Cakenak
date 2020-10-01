package Utils;

import java.io.IOException;

import dbrepo.RepoPembeli;
import model.Pembeli;

public class insertcashpembeli {

	
	public static void main(String[] args) throws IOException {
		Pembeli pembeli = new Pembeli();
		RepoPembeli repoPembeli = new RepoPembeli();
		
//		for (int i = 1; i < 11; i++) {
//		pembeli.setIdpembeli("rowPembeli" + i);
//		pembeli.setUsername("user_test_" + i);
//		pembeli.setCash(10000000);
//		repoPembeli.updateBalancePembeli(pembeli);
//		}
		pembeli.setIdpembeli("rowPembeli0");
		pembeli.setUsername("user_test_1");
		pembeli.setCash(10000000);
		repoPembeli.updateBalancePembeli(pembeli);
	}
	
}
