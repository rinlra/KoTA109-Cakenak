
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoCart;
import dbrepo.RepoKue;
import model.Cart;
import model.Kue;
import model.Pembeli;


/**
 * Menambahkan objek cart kedalam DB
 * @KoTA109
 */
@WebServlet("/addCart")
public class AddCart extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1633093337750611777L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        RepoCart repoCart = new RepoCart();
       
        String idkue = request.getParameter("idkue");
        RepoKue repoKue = new RepoKue();
        Kue kue = repoKue.getKueById(idkue);
        Kue newkue = new Kue();
        
        if (kue.getAvailablestock() > 0) {
        System.out.println("add ke cart untuk id kue : " + request.getParameter("idkue"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        //get login user id
        Pembeli user = (Pembeli) request.getSession().getAttribute("LoginUser");
        
        Cart cart = new Cart();
        
        
        try {
        	newkue.setAvailablestock(kue.getAvailablestock()-quantity);
        	if (newkue.getAvailablestock() < 0) {
        		
        		response.setStatus(500);
        	} else {
        		newkue.setIdkue(idkue);
                repoKue.updateQuantityKueAvailable(newkue);
                cart.setIdkue(idkue);
                cart.setQuantity(quantity);
        	}
        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("error memasukan jumlah kue kedalam cart");
            response.flushBuffer();
        }
        
        
        cart.setIdpembeli(user.getIdpembeli());
        
        System.out.println("add ke cart untuk id pembeli : " + cart.getIdpembeli() );
        
        if(repoCart.addCart(cart)){
            response.getWriter().print(repoCart.getNumberOfCartsForUser(user.getIdpembeli()));
        }
        }
        
    }

    

}
