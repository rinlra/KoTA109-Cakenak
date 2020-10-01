package Utils;

import java.io.IOException;
import java.util.ArrayList;

import dbrepo.RepoCart;
import dbrepo.RepoKue;
import dbrepo.RepoPembayaran;
import dbrepo.RepoPembeli;
import model.Cart;
import model.CartItem;
import model.Kue;
import model.Pembeli;

public class ThreadTest implements Runnable {
	
	Pembeli pembeli;
	ArrayList<CartItem> productCart;
	
	public ThreadTest (String idpembeli, String idkue, int quantity) throws IOException {
		RepoPembeli repoPembeli = new RepoPembeli();
		RepoKue repoKue = new RepoKue();
		pembeli = new Pembeli();
		Cart cart = new Cart();
		RepoCart cartModel = new RepoCart();
		Kue kue = repoKue.getKueById(idkue);
		pembeli = repoPembeli.getPembeliById(idpembeli);
		cart.setIdkue(idkue);
		cart.setIdpembeli(idpembeli);
		cart.setQuantity(quantity);
		kue.setAvailablestock(kue.getAvailablestock()-quantity);
		repoKue.updateQuantityKueAvailable(kue);
		cartModel.addCart(cart);
		productCart = cartModel.getProductFromCart(pembeli.getIdpembeli());
	}
	
	public ThreadTest () {
		
	}

	@Override
	public void run() {
		try {
			new RepoPembayaran().startPayment(pembeli, productCart);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
