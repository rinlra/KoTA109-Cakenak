
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoCart;
import dbrepo.RepoKue;
import model.Cart;
import model.Kue;


/**
 * delete cart from user cart in DB
 * @KoTA109
 */
@WebServlet("/DeleteCart")
public class DeleteCart extends HttpServlet {
 /**
	 * 
	 */
	private static final long serialVersionUID = 8151441244357387562L;
RepoCart repoCart;
   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        repoCart = new RepoCart();
        RepoKue repoKue = new RepoKue();
        Kue kue = new Kue();
        String id = request.getParameter("id");
       
        Cart cart = repoCart.getCart(id);
        String idkue = cart.getIdkue();
        kue = repoKue.getKueById(idkue);
        kue.setAvailablestock(kue.getAvailablestock()+cart.getQuantity());
        repoKue.updateQuantityKueAvailable(kue);
        
        boolean addCart = repoCart.deleteCart(id);
        
        String nextJSP = "/CartHandlerServlet";
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.include(request, response);
    }

    
}
