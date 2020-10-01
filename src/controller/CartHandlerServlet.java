package controller;

/**
 * get object in cart db of user to display in checkout page
 * @author sara metwalli
 */
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoCart;
import dbrepo.RepoKue;
import dbrepo.RepoPenjual;
import model.CartItem;
import model.Kue;
import model.Pembeli;

@WebServlet("/CartHandlerServlet")
public class CartHandlerServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2146129636005114326L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<CartItem> carts = new ArrayList<CartItem>();
        Pembeli pembeli = (Pembeli) request.getSession().getAttribute("LoginUser");
        ArrayList<String> penjual = new ArrayList<String>();
        RepoPenjual repoPenjual = new RepoPenjual();
        RepoKue repoKue = new RepoKue();
        String idpembeli = pembeli.getIdpembeli();
        RepoCart cartModel = new RepoCart();
        carts = cartModel.getProductFromCart(idpembeli);

        carts.forEach(cart->{
        	try {
        		Kue kue = new Kue();
				kue = repoKue.getKueById(cart.getIdkue());
				penjual.add(repoPenjual.getPenjualById(kue.getIdpenjual()).getNamapenjual());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        });
        
        System.out.println(penjual);
        request.setAttribute("carts", carts);
        request.setAttribute("penjual", penjual);

        String nextJSP = "/cart.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }
}
