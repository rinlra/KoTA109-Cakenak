package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import model.Penjual;

@WebServlet(name = "Checkout", urlPatterns = {"/Checkout"})
public class Checkout extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 60202943088237472L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		ArrayList<CartItem> carts = new ArrayList<CartItem>();
        Pembeli pembeli = (Pembeli) request.getSession().getAttribute("LoginUser");
        String idpembeli = pembeli.getIdpembeli();
        RepoCart cartModel = new RepoCart();
        carts = cartModel.getProductFromCart(idpembeli);

        request.setAttribute("carts", carts);
        
        RepoKue repoKue = new RepoKue();
        RepoPenjual repoPenjual = new RepoPenjual();
        double total = 0;
        String message = "";
        
        //get user form session
        Pembeli user = (Pembeli) request.getSession().getAttribute("LoginUser");

        //get product of user
        ArrayList<CartItem> productCart = cartModel.getProductFromCart(user.getIdpembeli());
        ArrayList<Kue> kue = new ArrayList<Kue>();
        ArrayList<Penjual> penjual = new ArrayList<Penjual>();
        //-------------- Checkes ------------------
        //check  quantity
        for (CartItem cart : productCart) {

            total = cart.getHarga() * cart.getQuantity();
            
            //number of order greater than avalialbe
            if (cart.getQuantity() > cart.getQuantity_kue()) {
                message += "* anda mengorder " + cart.getQuantity() + " dari produk " + cart.getNama()
                		+ "(ID " + cart.getIdkue() + ")" 
                        + " yang hanya tersisa " + cart.getQuantity_kue() + " buah lagi :( <br/>";
            }
            
           kue.add(repoKue.getKueById(cart.getIdkue()));    
        }
        
        kue.forEach(kuecekout -> {
        	String idpenjual = kuecekout.getIdpenjual();
        	System.out.println(idpenjual);
        	try {
				penjual.add(repoPenjual.getPenjualById(idpenjual));
				System.out.println(penjual.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });


        //an error occure
        if (!message.trim().equals("")) {
            request.getSession().setAttribute("message", message);
            response.sendRedirect("Failed.jsp");
            return;
        }
        Map<Penjual, Kue> daftar = new HashMap<Penjual, Kue>();
        
        request.setAttribute("kue", kue);
        request.setAttribute("penjual", penjual);
        
        String nextJSP = "/checkout.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
        
	}
	
	@Override
    public String getServletInfo() {
        return "pay money for product of user";
    }// </editor-fold>
	
}
