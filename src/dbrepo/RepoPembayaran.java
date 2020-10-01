
package dbrepo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import Utils.TimestampCreator;
import model.CartItem;
import model.Kue;
import model.Pembeli;
import model.Transaksi;

/**
 * make payment transaction proccess
 * 
 * @KoTA109
 */
public class RepoPembayaran {

	TimestampCreator tc = new TimestampCreator();
	long ts;

	public synchronized boolean startPayment(Pembeli user, ArrayList<CartItem> sold) throws IOException {

		Kue kue = null;
		RepoTransaksi repoTransaksi = new RepoTransaksi();

		try {

			// update user balance
			new RepoPembeli().updateBalancePembeli(user);

			ts = tc.getCurTmstmp();

			// update amount of product on stock & insert into history
			RepoKue productModel = new RepoKue();
			for (CartItem itemSold : sold) {

				Kue product = new Kue();
//                kue = productModel.getKueById(itemSold.getIdkue());
				product.setIdkue(itemSold.getIdkue());
				System.out
						.println("pembeli : " + user.getIdpembeli() + " membeli kue dengan id : " + product.getIdkue());
				product.setPhysicalstock(itemSold.getQuantity_kue() - itemSold.getQuantity());
//                product.setAvailablestock(kue.getAvailablestock());
				productModel.updateQuantityKue(product);
				System.out.println("physical stock setelah dibeli : " + product.getPhysicalstock()
						+ " dengan quantity : " + itemSold.getQuantity());
				// save proccess in history
				Transaksi history = new Transaksi();
				history.setTglpembelian(LocalDateTime.of(LocalDate.now(), LocalTime.now()) + "");
				history.setIdkue(itemSold.getIdkue());
				history.setIdpembeli(user.getIdpembeli());
				history.setQuantity(itemSold.getQuantity());
				history.setTotaltransaksi(itemSold.getQuantity() * itemSold.getHarga());
				history.setFoto(" ");
				history.setNorekening(user.getNorekening());
				history.setStatussellerpaid(0);
				history.setIdpengiriman("_idkirim" + user.getIdpembeli() + ts);
				history.setStatustransaksi(1);
				repoTransaksi.addUserHistory(history);
			}

			// empty user cart
			new RepoCart().deleteUserCart(user.getIdpembeli());

			// commit
			// con.commit();
			// return defualt value of Database

			return true;
		} catch (IOException ex) {
			System.out.println("----Error in Transaction ----");
			ex.printStackTrace();
			return false;
		}
	}

	public boolean startPaymentWithPhoto(Pembeli user, ArrayList<CartItem> sold, String foto) throws IOException {

		Kue kue = null;

		try {

			// update user balance
			new RepoPembeli().updateBalancePembeli(user);

			ts = tc.getCurTmstmp();

			// update amount of product on stock & insert into history
			RepoKue productModel = new RepoKue();
			for (CartItem itemSold : sold) {

				Kue product = new Kue();
//                kue = productModel.getKueById(itemSold.getIdkue());
				product.setIdkue(itemSold.getIdkue());
				product.setPhysicalstock(itemSold.getQuantity_kue() - itemSold.getQuantity());
//                product.setAvailablestock(kue.getAvailablestock());
				productModel.updateQuantityKue(product);

				// save proccess in history
				Transaksi history = new Transaksi();
				history.setTglpembelian(LocalDateTime.of(LocalDate.now(), LocalTime.now()) + "");
				history.setIdkue(itemSold.getIdkue());
				history.setIdpembeli(user.getIdpembeli());
				history.setQuantity(itemSold.getQuantity());
				history.setTotaltransaksi(itemSold.getQuantity() * itemSold.getHarga());
				history.setFoto(foto);
				history.setNorekening(user.getNorekening());
				history.setStatussellerpaid(0);
				history.setIdpengiriman("idkirim" + user.getIdpembeli() + ts);
				history.setStatustransaksi(0);
				new RepoTransaksi().addUserHistory(history);
			}

			// empty user cart
			new RepoCart().deleteUserCart(user.getIdpembeli());

			// commit
			// con.commit();
			// return defualt value of Database

			return true;
		} catch (IOException ex) {
			System.out.println("----Error in Transaction ----");
			ex.printStackTrace();
			return false;
		}
	}

}
